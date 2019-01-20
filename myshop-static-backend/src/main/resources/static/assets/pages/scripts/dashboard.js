/**
 * 备注说明：var、let、const 的区别
 * var 定义的变量，没有块的概念，可以跨块访问, 不能跨函数访问。
 * let 定义的变量，只能在块作用域里访问，不能跨块访问，也不能跨函数访问。
 * const 用来定义常量，使用时必须初始化(即必须赋值)，只能在块作用域里访问，而且不能修改。
 * @type {{init}}
 */

const Dashboard = function() {
    let tabPanel;

    // Tab ID 前缀
    const TB_PANEL_ID_PREFIX = "tab_panel_";

    const initTabs = [
        '<iframe src="/dashboard" width="100%" height="100%" frameborder="0"></iframe>'
    ];

    /**
     * 初始化 Tab
     */
    const handlerInitTabPanel = function () {
        tabPanel = new TabPanel({
            renderTo: 'tabpanel',
            height: function () {
                var windowHeight = $(window).height(),
                    headerHeight = $(".page-header:visible").outerHeight(),
                    footerHeight = $(".page-footer:visible").outerHeight(),
                    tabHeight = windowHeight - headerHeight - footerHeight - 40;
                return tabHeight < 300 ? 300 : tabHeight
            },
            active: 0,
            items: [
                {id: TB_PANEL_ID_PREFIX + 'dashboard', title: '<i class="fa fa-home"></i> 首页', html: initTabs[0], closable: false},
            ]
        });
        $(window).resize();
    };

    /**
     * 新增页签
     * @param title 标题
     * @param url 地址
     */
    const handlerAddTabPanel = function (id, title, url) {
        // 判断页签是否已经打开，若已打开则激活
        let tabPanelActive = tabPanel.getTabPosision(TB_PANEL_ID_PREFIX + id);
        if (tabPanelActive > 0) {
            tabPanel.refresh(TB_PANEL_ID_PREFIX + id);
        }

        else {
            // 追加页签
            tabPanel.addTab({
                id: TB_PANEL_ID_PREFIX + id,
                title: title,
                html: '<iframe src="' + url + '" width="100%" height="100%" frameborder="0"></iframe>',
                closable: true,
                disabled: false
            });
        }
    };

    return {
        init: function() {
            handlerInitTabPanel();
        },

        addTabPanel: function (id, title, url) {
            handlerAddTabPanel(id, title, url);
        }
    };

}();

if (App.isAngularJsApp() === false) {
    jQuery(document).ready(function() {
        // init metronic core componets
        Dashboard.init();
    });
}