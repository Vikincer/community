package org.vikinc.community.cache;

import org.apache.commons.lang3.StringUtils;
import org.vikinc.community.dto.DTOTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<DTOTag> get(){
        ArrayList<DTOTag> dtoTags = new ArrayList<>();
        DTOTag program = new DTOTag();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html5","java","python","node.js","golang","c","c++","c#","objective-c","typescript"));

        DTOTag framework = new DTOTag();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laraver","spring","express","django","flask","yii","ruby-on-rails","tornado","koa","struts"));

        DTOTag server = new DTOTag();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubantu","centos","tomcat","unix","hadoop","windows-server"));

        DTOTag db = new DTOTag();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mangodb","sql","oracle","nosql","sqlserver","postgresql","sqlite","windows-server"));

        DTOTag tool = new DTOTag();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","idea","vim","eclipse","maven","svn","vs","hg"));

        dtoTags.add(program);
        dtoTags.add(framework);
        dtoTags.add(server);
        dtoTags.add(db);
        dtoTags.add(tool);
        return dtoTags;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<DTOTag> dtoTags = get();
        List<String> togList = dtoTags.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !togList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
