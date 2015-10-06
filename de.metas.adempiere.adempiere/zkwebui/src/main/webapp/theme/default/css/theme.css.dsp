<%@ page contentType="text/css;charset=UTF-8" %>
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>

html,body {
	margin: 0;
	padding: 0;
	height: 100%;
	width: 100%;
	background-color: #D4E3F4;
	overflow: hidden;
}

<%-- login --%>
.login-window {
	background-color: #E5E5E5;
}

.login-box-body {
	width: 660px;
	background-image: url(../images/login-box-bg.png);
	background-repeat: repeat-y;
	background-color: transparent;
	z-index: 1;
	padding: 0;
	margin: 0;
	text-align: center;
	padding-bottom: 100px;
}

.login-box-header {
	background-image: url(../images/login-box-header.png);
	background-color: transparent;
	z-index: 2;
	height: 54px;
	width: 660px;
}

.login-box-header-txt {
	color: white;
	font-weight: bold;
	position: relative;
	top: 30px;
}

.login-box-header-logo {
	padding-top: 20px;
	padding-bottom: 25px;
}

.login-box-footer {
	background-image: url(../images/login-box-footer.png);
	background-position: top right;
	background-attachment: scroll;
	background-repeat: repeat-y;
	z-index: 2;
	height: 110px;
	width: 660px;
}

.login-box-footer-pnl {
	width: 604px;
	margin-left: 10px;
	margin-right: 10px;
	padding-top: 40px;
}

.login-label {
	color: black;
	text-align: right;
	width: 40%;
}

.login-field {
	text-align: left;
	width: 55%;
}

.login-btn {
	height: 36px;
	width: 72px;
}

.login-east-panel, .login-west-panel {
	width: 350px;
	background-color: #E0EAF7;
	position: relative;
}

<%-- header --%>
.desktop-header-left {
	margin: 0;
	margin-left: 5px;
	margin-top: 3px;
}

.desktop-header-right {
	margin: 0;
	margin-top: 3px;
	padding-right: 5px;
}

.disableFilter img {
	opacity: 0.2;
	filter: progid : DXImageTransform . Microsoft . Alpha(opacity = 20);
	-moz-opacity: 0.2;
}

.toolbar {
	padding: 0px;
}

.toolbar-button img {
	width: 22px;
	height: 22px;
	padding: 0px 1px 0px 1px;
	border-style: solid;
	border-width: 1px;
	border-color: transparent;
}

.embedded-toolbar-button img {
	width: 16px;
	height: 16px;
	padding: 0px 1px 0px 1px;
	border-style: solid;
	border-width: 1px;
	border-color: transparent;
}

.depressed img {
	border-style: inset;
	border-width: 1px;
	border-color: #9CBDFF;
	background-color: #C4DCFB;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	padding: 0px 1px 0px 1px;
}

.desktop-header {
	background-image: url(../images/header-bg.png);
	background-repeat: repeat-x;
	background-position: bottom left;
	background-color: white;
	width: 100%;
	height: 35px;
}

.desktop-header-font {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
}

<%-- button --%>
.action-button {
	height: 32px;
	width: 48px;
}

.action-text-button {
	height: 32px;
	width: 80px;
}

.editor-button {
	width: 26px;
	padding: 0px;
}

.editor-button img {
	vertical-align: middle;
	text-align: center;
}

<%-- desktop --%>
div.wc-modal, div.wc-modal-none, div.wc-highlighted, div.wc-highlighted-none {
	background-color: white;
}

.desktop-tabpanel {
	margin: 0;
	padding: 0;
	border: 0;
	position: absolute;
}

.menu-search {
	background-color: #E0EAF7;
}

<%-- adwindow and form --%>
.adform-content-none {
	overflow: auto;
	position: absolute;
	width: 100%;
	margin: 3px;
}

.adwindow-status {
	background-color: #E0EAF7;
	height: 20px;
}

.adwindow-nav {
}

.adwindow-left-nav {
	border-right: 1px solid #7EAAC6;
	border-left: none;
}

.adwindow-right-nav {
	border-left: 1px solid #7EAAC6;
	border-right: none;
}

.adwindow-nav-content {
	background-color: #E0EAF7;
	height: 100%;
}

.adwindow-toolbar {
	border: 0px;
}

.adwindow-navbtn-dis, .adwindow-navbtn-sel, .adwindow-navbtn-uns {
	border: 0px;
	margin-top: 3px;
	padding-top: 2px;
	padding-bottom: 2px;
}

.adwindow-navbtn-sel {
	background-color: #9CBDFF;
	font-weight: bold;
	color: #274D5F;
	cursor: pointer;
	border-top: 2px solid #7EAAC6;
	border-bottom: 2px solid #7EAAC6;
}

.adwindow-left-navbtn-sel {
	border-left: 2px solid #7EAAC6;
	border-right: none;
	text-align: right;
	-moz-border-radius-topleft: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-bottom-left-radius: 5px;
	background-color: #d1e7f6 !important;
	background-image: url(../images/adtab-left-bg.png);
	background-repeat: repeat-y;
	background-position: top right;
}

.adwindow-right-navbtn-sel {
	border-right: 2px solid #7EAAC6;
	border-left: none;
	text-align: left;
	-moz-border-radius-topright: 5px;
	-moz-border-radius-bottomright: 5px;
	-webkit-border-top-right-radius: 5px;
	-webkit-border-bottom-right-radius: 5px;
	background-color: #d1e7f6 !important;
	background-image: url(../images/adtab-right-bg.png);
	background-repeat: repeat-y;
	background-position: top left;
}

.adwindow-navbtn-uns {
	background-color: #C4DCFB;
	font-weight: normal;
	color: #274D5F;
	cursor: pointer;
}

.adwindow-navbtn-dis {
	background-color: #C4DCFB;
}

.adwindow-navbtn-uns, .adwindow-navbtn-dis {
	border-top: 1px solid #CCCCCC;
	border-bottom: 1px solid #CCCCCC;
}

.adwindow-left-navbtn-uns, .adwindow-left-navbtn-dis {
	border-left: 1px solid #CCCCCC;
	border-right: none;
	text-align: right;
	-moz-border-radius-topleft: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-bottom-left-radius: 5px;
}

.adwindow-right-navbtn-uns, .adwindow-right-navbtn-dis {
	border-right: 1px solid #CCCCCC;
	border-left: none;
	text-align: left;
	-moz-border-radius-topright: 5px;
	-moz-border-radius-bottomright: 5px;
	-webkit-border-top-right-radius: 5px;
	-webkit-border-bottom-right-radius: 5px;
}

<%-- ad tab --%>
.adtab-body {
	position: absolute;
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	border: none;
}

.adtab-content {
	margin: 0;
	padding: 0;
	border: none;
	overflow: auto;
	width: 100%;
	height: 100%;
	position: absolute;
}

.adtab-grid-panel {
	position: absolute;
	overflow: hidden;
	width: 100%;
	height: 100%;
}

.adtab-grid {
	width: 100%;
	position: absolute;
}

.adtab-tabpanels {
	width: 80%;
	border-top: 1px solid #9CBDFF;
	border-bottom: 1px solid #9CBDFF;
	border-left: 2px solid #9CBDFF;
	border-right: 2px solid #9CBDFF;
}

<%-- status bar --%>
.status {
	width: 100%;
	height: 20px;
}

.status-db {
	padding-top: 0;
	pdding-bottom: 0;
	padding-left: 5px;
	padding-right: 5px;
	cursor: pointer;
	width: 100%;
	height: 100%;
	margin: 0;
	border-left: solid 1px #9CBDFF;
}

.status-info {
	padding-right: 10px;
	border-left: solid 1px #9CBDFF;
}

.status-border {
	border: solid 1px #9CBDFF;
}

.form-button {
	width: 99%;
}

<%-- Combobox --%>
.z-combobox-disd {
	color: black !important; cursor: default !important; opacity: 1; -moz-opacity: 1; -khtml-opacity: 1; filter: alpha(opacity=100);
}

.z-combobox-disd * {
	color: black !important; cursor: default !important;
}

.z-combobox-text-disd {
	background-color: #ECEAE4 !important;
}

<%-- Button --%>
.z-button-disd {
	color: black; cursor: default; opacity: .6; -moz-opacity: .6; -khtml-opacity: .6; filter: alpha(opacity=60);
}

<%-- highlight focus form element --%>
input:focus, textarea:focus, .z-combobox-inp:focus, z-datebox-inp:focus {
	border: 1px solid #0000ff;
}

.mandatory-decorator-text {
	text-decoration: none; font-size: xx-small; vertical-align: top; color:red;
}
<%-- menu tree cell --%>
div.z-tree-body td.menu-tree-cell {
	cursor: pointer;
	padding: 0 2px;
   	font-size: ${fontSizeM};
   	font-weight: normal;
   	overflow: visible;
}

div.menu-tree-cell-cnt {
	border: 0; margin: 0; padding: 0;
	font-family: ${fontFamilyC};
	font-size: ${fontSizeM}; font-weight: normal;
    white-space:nowrap
}

td.menu-tree-cell-disd * {
	color: #C5CACB !important; cursor: default!important;
}

td.menu-tree-cell-disd a:visited, td.menu-tree-cell-disd a:hover {
	text-decoration: none !important;
	cursor: default !important;;
	border-color: #D0DEF0 !important;
}

div.z-dottree-body td.menu-tree-cell {
	cursor: pointer; padding: 0 2px;
	font-size: ${fontSizeM}; font-weight: normal; overflow: visible;
}

div.z-filetree-body td.menu-tree-cell {
	cursor: pointer; padding: 0 2px;
	font-size: ${fontSizeM}; font-weight: normal; overflow: visible;
}

div.z-vfiletree-body td.menu-tree-cell {
	cursor: pointer; padding: 0 2px;
	font-size: ${fontSizeM}; font-weight: normal; overflow: visible;
}
