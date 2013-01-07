(function ($) {
    var UISpaceNavigation = {
        uicomponentId: '',
        mySpaceRestUrl: '',
        selectSpaceAction: '',
        noSpace: '',
        lastSearchKeyword: '',
        init: function (uicomponentId, mySpaceRestUrl, defaultValueForTextSearch, noSpace, selectSpaceAction) {
            var me = UISpaceNavigation;
            me.mySpaceRestUrl = mySpaceRestUrl;
            me.lastSearchKeyword = "";
            me.selectSpaceAction = selectSpaceAction;
            me.noSpace = noSpace;
            me.uicomponentId = uicomponentId;
            var textField = $('#' + uicomponentId).find("input.searchText:first");

            textField.on('keyup', function (e) {
                me.onTextSearchChange(this);
            });

            textField.on('focus', function () {
                $(this).attr('class', 'searchText Focus');
            });

            textField.on('blur', function () {
							  $(this).attr('class', 'searchText LostFocus');
            });

            textField.on('click', function (e) {
                e.stopPropagation();
            });

        },
        requestData: function (keyword) {

            var url = UISpaceNavigation.mySpaceRestUrl + "?keyword=" + keyword;
            $.getJSON(url, {}, function (data) {
                UISpaceNavigation.render(data);
            });
        },
        render: function (dataList) {
            UISpaceNavigation.dataList = dataList;
            var spacesListREsult = $('#' + UISpaceNavigation.uicomponentId).find('ul.spaceNavigation:first');
            //var spaces = dataList.jsonList;
            var spaces = dataList;
            var groupSpaces = '';
            var spaceUrl = window.location.protocol + "//" + window.location.host + "/";
            for (i = 0; i < spaces.length; i++) {
                var spaceId = spaces[i].id;
                var name = spaces[i].displayName;
                var imageUrl = spaces[i].avatarUrl;
                if (!imageUrl) {
                    imageUrl = "/social-resources/skin/ShareImages/SpaceImages/SpaceLogoDefault_61x61.gif";
                }
                var spaceDiv = "<li class='spaceItem'>" + 
															 "  <a class='spaceIcon'" + "' href='" + (spaceUrl + spaces[i].url) + "'>" +
															 "    <img src='" + imageUrl + "'/>" + name + 
															 "  </a>" +
															 "</li><br/>";
                groupSpaces += spaceDiv;
            }

            if (groupSpaces != '') {
                spacesListREsult.html(groupSpaces);
            } else {
                spacesListREsult.html(UISpaceNavigation.noSpace);
            }
        },
        onTextSearchChange: function (elm) {
            var textSearch = $(elm).val();

            if (textSearch != UISpaceNavigation.lastSearchKeyword) {
                UISpaceNavigation.lastSearchKeyword = textSearch;
                UISpaceNavigation.requestData(textSearch);
            }
        }
    };

    return UISpaceNavigation;
})($);
