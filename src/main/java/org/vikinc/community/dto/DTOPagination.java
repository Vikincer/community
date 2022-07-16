package org.vikinc.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DTOPagination {
    private List<DTOQuestion> questionList;
    private boolean showPrevious;   //上一页
    private boolean showNext;       //下一页
    private boolean showFirstPage;  //第一页
    private boolean showEndPage;    //最后一页
    private Integer page;
    private List<Integer> pages = new ArrayList<>();    //导航栏页码数

    /**
     *
     * @param total 总条数
     * @param page  当前页码
     * @param size  显示数量
     */
    public Integer setPagination(Integer total, Integer page, Integer size) {
        this.page = page;
        Integer totalPage = 0;  //设置总页数

        if(total %size == 0){
            totalPage = total / size;
        }else{
            totalPage = total / size +1;
        }
        if(page < 1)            //容错 新版本 不允许 limit中有负数
            page = 1;
        if(page > totalPage)
            page = totalPage;
        pages.add(page);                //设置页码
        for (int i = 1; i<=3; i++){
            if(page-i >0)
                pages.add(0,page-i);      //展示当前页码的前3页
            if(page+i <= totalPage)
                pages.add(page+i);      //展示当前页码的后3页
        }


        //是否展示 上一页
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //是否展示 下一页
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
        return size*(page-1);
    }



}
