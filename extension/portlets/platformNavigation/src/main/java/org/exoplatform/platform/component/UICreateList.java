package org.exoplatform.platform.component;

import org.exoplatform.cs.event.UICreateEvent;
import org.exoplatform.forum.create.UICreatePoll;
import org.exoplatform.forum.create.UICreateTopic;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.wcm.webui.Utils;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.commons.UIDocumentSelector;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIComponent;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="rtouzi@exoplatform.com">rtouzi</a>
 * @date 09/11/12
 */
@ComponentConfig(
        template = "app:/groovy/platformNavigation/portlet/UICreatePlatformToolBarPortlet/UICreateList.gtmpl",
        events = {
                @EventConfig(
                        listeners = UICreateList.AddEventActionListener.class
                ),
                @EventConfig(
                        listeners = UICreateList.PollActionListener.class

                ),
                @EventConfig(
                        listeners = UICreateList.TopicActionListener.class

                ),
                @EventConfig(
                        listeners = UICreateList.WikiActionListener.class

                ),

                @EventConfig
                        (listeners = UICreateList.UploadActionListener.class
                        ),
                @EventConfig(
                        listeners = UICreateList.CancelActionListener.class
                )
        }
)

public class UICreateList extends UIContainer {
    static String parStatus;
    private static Log log = ExoLogger.getLogger(UICreateList.class);

    public static void remove(UICreateList uiform) {
        List<UIComponent> uilist = uiform.getChildren();
        List<String> lisID = new ArrayList<String>();
        if (uilist.size() != 0) {
            for (UIComponent uIComponent : uilist) {
                lisID.add(uIComponent.getId());
            }
            for (String id : lisID) {
                uiform.removeChildById(id);

            }
        }
    }


    static public class AddEventActionListener extends EventListener<UICreateList> {


        public void execute(Event<UICreateList> event)
                throws Exception {

            UICreatePlatformToolBarPortlet uiForm = (UICreatePlatformToolBarPortlet) event.getSource().getAncestorOfType(UICreatePlatformToolBarPortlet.class);
            UICreateList uisource = (UICreateList) event.getSource();
            remove(uisource);
            uisource.addChild(UICreateEvent.class, null, null).setRendered(true);
            event.getRequestContext().addUIComponentToUpdateByAjax(uisource);
            event.getRequestContext().addUIComponentToUpdateByAjax(uiForm);
        }

    }

  static public class PollActionListener extends EventListener<UICreateList> {

    public void execute(Event<UICreateList> event) throws Exception {

      parStatus = event.getRequestContext().getRequestParameter(OBJECTID);
      UICreateList uisource = event.getSource();
      remove(uisource);
      uisource.addChild(UICreatePoll.class, null, null).setRendered(true);
      event.getRequestContext().addUIComponentToUpdateByAjax(uisource);
    }

  }


  static public class TopicActionListener extends EventListener<UICreateList> {

    public void execute(Event<UICreateList> event) throws Exception {

      parStatus = event.getRequestContext().getRequestParameter(OBJECTID);
      UICreateList uisource = event.getSource();
      remove(uisource);
      uisource.addChild(UICreateTopic.class, null, null).setRendered(true);
      event.getRequestContext().addUIComponentToUpdateByAjax(uisource);
    }
  }


    static public class WikiActionListener extends EventListener<UICreateList> {

        public void execute(Event<UICreateList> event)
                throws Exception {
            UICreatePlatformToolBarPortlet uiForm = (UICreatePlatformToolBarPortlet) event.getSource().getAncestorOfType(UICreatePlatformToolBarPortlet.class);
            parStatus = event.getRequestContext().getRequestParameter("objectId");
            UICreateList uisource = (UICreateList) event.getSource();
            remove(uisource);
            uisource.addChild(UICreateForm.class, null, null).setRendered(true);
            event.getRequestContext().addUIComponentToUpdateByAjax(uisource);
            event.getRequestContext().addUIComponentToUpdateByAjax(uiForm);
        }
    }


    /**
     * *
     */

    static public class UploadActionListener extends EventListener<UICreateList> {

        public void execute(Event<UICreateList> event) throws Exception {
            UICreateList uiCraeteList = event.getSource();
            try {
                UIDocumentSelector selector = uiCraeteList.createUIComponent(UIDocumentSelector.class, null, "UploadFileSelector");
                Utils.createPopupWindow(uiCraeteList, selector, "UploadFileSelectorPopUpWindow", 335);
            } catch (Exception e) {
                //TODO add log
            }
        }
    }


    static public class CancelActionListener extends EventListener<UICreateList> {


        public void execute(Event<UICreateList> event)
                throws Exception {
            UICreatePlatformToolBarPortlet uiForm = (UICreatePlatformToolBarPortlet) event.getSource().getAncestorOfType(UICreatePlatformToolBarPortlet.class);
            UICreateList uiparent = event.getSource();
            WebuiRequestContext context = event.getRequestContext();
            remove(uiparent);
            context.addUIComponentToUpdateByAjax(uiparent);
            context.addUIComponentToUpdateByAjax(uiForm);


        }
    }

    public static String getParStatus() {
        return parStatus;
    }

    public String[] getActions() {
        return new String[]{"Topic", "Poll", "AddEvent", "Wiki", "Upload"};
    }
}
