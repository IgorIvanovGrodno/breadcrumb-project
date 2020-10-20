package apps.breadcrumb_project.components.content.breadcrumb;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Breadcrumb extends WCMUsePojo {
    private List<InfoOfPage> listOfPagesInfo;

    @Override
    public void activate() throws Exception {
        Page currentPage=getCurrentPage();
        listOfPagesInfo = getAllParentPages(currentPage);
        Collections.reverse(listOfPagesInfo);
    }

    private List<InfoOfPage> getAllParentPages(Page page){
        List<InfoOfPage> listOfParentPages = new ArrayList();
        if(page!=null) {
            listOfParentPages.add(convertToInfoOfPage(page, false));
            page=page.getParent();
            while(page!=null){
                if(!page.isHideInNav()){
                    listOfParentPages.add(convertToInfoOfPage(page, true));
                }
                page=page.getParent();
            }
        }
        return listOfParentPages;
    }

    private InfoOfPage convertToInfoOfPage(Page page, boolean active) {
        String title;
        if(page.getNavigationTitle()!=null&&!page.getNavigationTitle().isEmpty()){
            title=page.getNavigationTitle();
        }else {
            if (page.getPageTitle()!=null&&!page.getPageTitle().isEmpty()){
                title=page.getPageTitle();
            }else {
                title=page.getTitle();
            }
        }
        String url = "http://localhost:4502"+page.getPath()+".html";
        return new InfoOfPage(title, url, active);
    }


    public List<InfoOfPage> getListOfPagesInfo() {
        return listOfPagesInfo;
    }

    public class InfoOfPage{

        private String title;
        private String url;
        private boolean active;

        public InfoOfPage(String title, String url, boolean active) {
            this.title = title;
            this.url = url;
            this.active = active;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public boolean isActive() {
            return active;
        }
    }
}
