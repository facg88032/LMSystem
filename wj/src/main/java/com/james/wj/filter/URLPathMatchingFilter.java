package com.james.wj.filter;

import com.james.wj.service.AdminPermissionService;
import com.james.wj.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {

    @Autowired
    AdminPermissionService adminPermissionService;


    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 放行 options 请求
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        if (null==adminPermissionService) {
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
        }

        String requestAPI = getPathWithinApplication(request);
        System.out.println("訪問接口：" + requestAPI);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            System.out.println("需要登入");
            return false;
        }
        System.out.println("不需要登入");

        // 過濾接口是否需要特定權限
        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if (!needFilter) {
            System.out.println("接口：" + requestAPI + "無需權限");
            return true;
        } else {
            System.out.println("驗證訪問權限：" + requestAPI);
            // 判斷當前的用戶是否有權限
            boolean hasPermission = false;
            String username = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(username);
            for (String api : permissionAPIs) {
                if (api.equals(requestAPI)) {
                    hasPermission = true;
                    break;
                }
            }

            if (hasPermission) {
                System.out.println("訪問權限：" + requestAPI + "驗證成功");
                return true;
            } else {
                System.out.println("當前用戶沒有放問接口" + requestAPI + "的權限");
                return false;
            }
        }
    }
}

