/*
SQLyog Trial v13.1.8 (64 bit)
MySQL - 5.7.36 : Database - wj
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wj` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `wj`;

/*Table structure for table `admin_menu` */

DROP TABLE IF EXISTS `admin_menu`;

CREATE TABLE `admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `name_zh` varchar(64) DEFAULT NULL,
  `icon_cls` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `admin_menu` */

insert  into `admin_menu`(`id`,`path`,`name`,`name_zh`,`icon_cls`,`component`,`parent_id`) values 
(1,'/admin','AdminIndex','首頁','HomeFilled','AdminIndex/index',0),
(2,'/admin/dashboard','DashboardAdmin','運行情況',NULL,'dashboard/admin/index',1),
(3,'/admin','User','用戶管理','User','AdminIndex/index',0),
(4,'/admin','Content','內容管理','Tickets','AdminIndex/index',0),
(5,'/admin','System','系統配置','Tools','AdminIndex/index',0),
(6,'/admin/user/profile','Profile','用戶訊息',NULL,'user/UserProfile',3),
(7,'/admin/user/role','Role','角色配置',NULL,'user/Role',3),
(8,'/admin/content/book','BookManagement','圖書館裡',NULL,'content/BookManagement/index',4),
(9,'/admin/content/banner','BannerManagement','廣告管理',NULL,'content/BannerManagement',4),
(10,'/admin/content/article','ArticleManagement','文章管理',NULL,'content/ArticleManagement',4);

/*Table structure for table `admin_permission` */

DROP TABLE IF EXISTS `admin_permission`;

CREATE TABLE `admin_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc_` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admin_permission` */

insert  into `admin_permission`(`id`,`name`,`desc_`,`url`) values 
(1,'users_management','用戶管理','/api/admin/user'),
(2,'roles_management','腳色管理','/api/admin/role'),
(3,'content_management','內容管理','/api/admin/content');

/*Table structure for table `admin_role` */

DROP TABLE IF EXISTS `admin_role`;

CREATE TABLE `admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `name_zh` varchar(100) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `admin_role` */

insert  into `admin_role`(`id`,`name`,`name_zh`,`enabled`) values 
(1,'sysAdmin','管理員',1),
(2,'contentManager','內容管理員',1),
(3,'visitor','訪客',1);

/*Table structure for table `admin_role_menu` */

DROP TABLE IF EXISTS `admin_role_menu`;

CREATE TABLE `admin_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) DEFAULT NULL,
  `mid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8mb4;

/*Data for the table `admin_role_menu` */

insert  into `admin_role_menu`(`id`,`rid`,`mid`) values 
(121,1,1),
(122,1,2),
(123,1,3),
(124,1,6),
(125,1,7),
(126,1,4),
(127,1,8),
(128,1,9),
(129,1,10),
(130,1,5),
(188,2,1),
(189,2,2),
(190,2,4),
(191,2,8),
(192,2,9),
(193,2,10),
(199,3,1),
(200,3,2);

/*Table structure for table `admin_role_permission` */

DROP TABLE IF EXISTS `admin_role_permission`;

CREATE TABLE `admin_role_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `rid` int(20) DEFAULT NULL,
  `pid` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_permission_role_1` (`rid`),
  KEY `fk_role_permission_permission_1` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;

/*Data for the table `admin_role_permission` */

insert  into `admin_role_permission`(`id`,`rid`,`pid`) values 
(154,2,3),
(170,1,1),
(171,1,2),
(172,1,3);

/*Table structure for table `admin_user_role` */

DROP TABLE IF EXISTS `admin_user_role`;

CREATE TABLE `admin_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operator_role_operator_1` (`uid`),
  KEY `fk_operator_role_role_1` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user_role` */

insert  into `admin_user_role`(`id`,`uid`,`rid`) values 
(90,1,1),
(91,2,3),
(92,3,2),
(93,5,3);

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cover` varchar(255) DEFAULT '',
  `title` varchar(255) NOT NULL DEFAULT '',
  `author` varchar(255) DEFAULT '',
  `date` varchar(20) DEFAULT '',
  `press` varchar(255) DEFAULT '',
  `abs` varchar(255) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_category_on_cid` (`cid`),
  CONSTRAINT `fk_book_category_on_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`cover`,`title`,`author`,`date`,`press`,`abs`,`cid`) values 
(68,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518001/wj/tqnfst8pm3j7gskfgbqj.jpg','半自白','橫山秀夫','2021-12','春天出版社','主動自首，又對案發過程坦白招認的兇手，卻對自首前的行蹤絕口不提，\n在這整整兩天的時間裡，他到底去了哪裡？做了什麼？\n連殺人行兇的細節都已全數供出，究竟還有什麼不能說的秘密？',1),
(69,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637517772/wj/poolufh9c5tgowp9ycfv.jpg','時間裡的孩子','伊恩．麥克尤恩','2021-10','馬可孛羅','時間裡的你，究竟長得是什麼模樣？\n漂流在時間之河的人們，將如何從悲傷解放，獲得救贖？',1),
(70,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637512915/wj/pmbphr0gpjop3nuc3gq7.jpg','婚姻生活','塔雅莉‧瓊斯','2021-11','時報出版','婚姻是一場生活的考驗。\n當考驗來臨時，人們才能懂得這一切的意義。',1),
(113,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518213/wj/uljcdswylvmlifybpetg.jpg','靈魂穿越手稿','亞歷斯．蘭德金 ','2021-9','寂寞','一個環形故事，可以從頭，也可以從中間看；說是結尾，卻又是另一個起點。讚翻了。──傑夫·賈維斯　《People》雜誌評論人、《紐約日報》週日主編、《舊金山觀察家報》專欄作家',1),
(114,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518469/wj/yle9h9kgo0nqvv6ciaje.jpg','無論如何都要活著','朝井遼','2021-9','采實文化','『你幸福嗎？』',1),
(115,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518626/wj/awpaoxqtuk9brzncy7ux.jpg','山茶花文具店','小川糸','2017-8','圓神','不論是想說出口的「謝謝」，還是說不出口的「抱歉」，\n那些無法言說的話語，就由我為您投遞吧──',4),
(116,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518700/wj/w2iwavgtuf2o7jiqrs1n.jpg','無名之子','布蘭登．山德森','奇幻基地','2021-10','擁有光明力量的盲眼歌女，對上與惡魔交易的莊園之主\n以卡牌桌遊魔法風雲會為舞台',2),
(117,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518765/wj/riqg6wqlcu11zaxornde.jpg','紅皇后','胡安・高美','2021-11','尖端','《紅皇后》描寫一個專門調查離奇詭異懸案的神祕歐洲組織，其中每位探員──「紅皇后」──都會搭配一個「侍從」。小說主角安東妮亞和她的搭檔尤恩，將追查一樁連續殺人事件。',2),
(118,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637518847/wj/x9gw9is8g3vxnxyl41im.jpg','希望之罪','雫井脩介','2021-11','春天出版社','故事中陷入困境才顯現的家人心情以及兒子的決定，除了讓我覺得悲傷，還有一股無法言喻的情感湧上心頭。──中江有里',1),
(119,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519051/wj/rjgncrmuqq0lm5fzmxfe.jpg','喜鵲謀殺案','安東尼．赫洛維茲','2021-8','春天出版社','在艾倫的新作中，艾提克思．彭德來到派伊府邸──一座鄉村內的莊園調查一樁謀殺案。是的，其中有死屍和許多各懷鬼胎的嫌犯。然而隨著情節的推演，蘇珊不禁越來越懷疑，在這份書稿字裡行間隱藏著另一個故事：一個充斥著嫉妒、貪婪、冷酷的野心，以及謀殺的真實故事……',2),
(120,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519216/wj/ewfckiuvpqmhy5uximyi.jpg','杜甫：中國最偉大的詩人','洪業','2021-10','聯經出版公司','杜甫一生歷盡美好和滄桑，宛若大唐縮影。從年少即展才智，與詩朋酒友裘馬輕狂，而後胸懷抱負卻礙於官場權計而不第，看透朝綱百態。對人民仍懷有深情掛念的他，以詩為聲，紀事寫實。本書將杜甫生平事件搭配詩作兩相對照，更能體會其作品被譽為「詩史」之深意。',3),
(121,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519389/wj/dvjtw7rl19yizscbbhxa.jpg','厭世國文教室：古文青生涯檔案','厭世國文老師','2020-11','究竟','設計對白＋群組對話＋有所本的「輔導紀錄」，\n厭世國文老師帶你了解那些課本沒寫、老師來不及教，\n但絕對讓人超有感的古人真心話和小劇場。\n讓古文不廢，人生不厭，還能讀出真滋味！',3),
(122,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519566/wj/tbjo5dpwvmjpxmfoj9mh.jpg','批判思考','賴俊雄','2020-8','聯經出版公司','批判思考：當代文學理論十二講》深入淺出地帶領讀者掌握與當代文學批判相關的基礎理論與知識，並在每一章節尾聲提出幾個問題與思辨，激發讀者運用文學理論提升文學文本分析和解決現實問題的能力。',3),
(123,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519637/wj/qmsnkbhnrdszc5tdhdwz.jpg','此生如鴿','約翰‧勒卡雷','2021-2','木馬文化','首度談到自己對世紀間諜金•費爾比的看法；他諸多個性鮮明、設定生動的角色來源，他的小說改編成電視電影的經歷，與好萊塢知名演員、導演、編劇打過的交道…',3),
(124,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519795/wj/lixxprjybrp79a9wzjrp.jpg','投資最重要的事','霍華．馬克斯','2017-2','商業周刊','霍華．馬克斯濃縮歷年備忘錄及價值投資心得，總結為二十項原則，包涵著名的「第二層思考」、價格與價值的關系、耐心等待機會、避開投資陷阱、對抗情緒帶來的負面影響……含括所有價值投資者的關鍵面向，處處都是洞見與啟發。',5),
(125,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519879/wj/tfggkm7xvb3phkamlkxv.jpg','初學者基礎重訓指南','凱爾．亨特','2021-9','采實文化','針對背部、手臂、腿部、核心、臀部等5大主要部位肌群，提供全面的訓練動作，不管是居家訓練或機械負重，都能安全又有效的進行鍛鍊。',4),
(127,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637519938/wj/gwcfgippjb8tfepz0rem.jpg','灰階思考','謝孟恭','2021-4','天下文化','零到一之間，有無限個數字，\n黑與白之間，也有無限的色階。\n面對多變的市場，跳脫黑白思維，\n才能看見無限大的價值！',5),
(128,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637520613/wj/knk9lcwdyvrnq0pzj3mg.jpg','Servlet&JSP技術手冊：邁向Spring Boot','林信良','2021-5','碁峰','介紹OWASP TOP 10、CWE、CVE，討論注入攻擊、會話安全、密碼管理、Java EE安全機制、CSRF等Web安全基本概念。',6),
(129,'http://res.cloudinary.com/dsvi2yzns/image/upload/v1637520765/wj/posjljfy9382xc3lgozp.jpg','一杯熱奶茶的等待','詹馥華','2021-11','蓋亞','渴望溫暖的人，終會彼此擁抱。\n相遇的契機，都是熱奶茶。',2);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`name`) values 
(1,'文學'),
(2,'流行'),
(3,'文化'),
(4,'生活'),
(5,'經營'),
(6,'科技');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` char(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`salt`,`name`,`phone`,`email`,`enabled`) values 
(1,'admin','8ef34182ec3d28edb29bd6dd3c969ccb','y4gi+V47hFMn/cGmcTGBJw==','管理员','0912777777','fkkyyg@gmial.com',1),
(2,'test','85087738b6c1e1d212683bfafc163853','JBba3j5qRykIPJQYTNNH9A==','測試','0933444777','xxx@yyy.coom',1),
(3,'editor','8583a2d965d6159edbf65c82d871fa3e','MZTe7Qwf9QgXBXrZzTIqJQ==','编辑','0982156111','ccc@kkk.com',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
