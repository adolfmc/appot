package cn.licoy.wdog.common.controller;

import cn.licoy.wdog.core.dto.appot.FindVenueDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class Pagination<T> {
      
    public static final int NUMBERS_PER_PAGE = 10;  
    private int totalPages; // 总页数  
    private int page;   // 当前页码  
    private List<T> resultList;    // 结果集存放List
  
    public Pagination(String sql, int currentPage, int numPerPage,JdbcTemplate jdbcTemplate ,RowMapper<T> rowMapper) {
        if (jdbcTemplate == null) {
            throw new IllegalArgumentException(  
                    "com.appot.util.Pagination.jTemplate is null,please initial it first. ");
        } else if (sql == null || sql.equals("")) {  
            throw new IllegalArgumentException(  
                    "com.appot.util.Pagination.sql is empty,please initial it first. ");
        }  
          
        String countSQL = getSQLCount(sql);  
        setPage(currentPage);
        setTotalPages(numPerPage,Integer.valueOf(jdbcTemplate.queryForMap(countSQL).get("cc").toString()));
        int startIndex = (currentPage - 1) * numPerPage;    //数据读取起始index  
          
        StringBuffer paginationSQL = new StringBuffer(" ");  
        paginationSQL.append(sql);

        paginationSQL.append(" limit "+ startIndex+","+numPerPage);

        setResultList(jdbcTemplate.query(paginationSQL.toString(),rowMapper));
    }  
      
    public String getSQLCount(String sql){  
        String sqlBak = sql.toLowerCase();  
        String searchValue = " from ";  
        String sqlCount = "select count(*) cc from "+ sql.substring(sqlBak.indexOf(searchValue)+searchValue.length(), sqlBak.length());
        return sqlCount;  
    }  
      
    public int getTotalPages() {  
        return totalPages;  
    }  
    public void setTotalPages(int totalPages) {  
        this.totalPages = totalPages;  
    }  
    public int getPage() {  
        return page;  
    }  
    public void setPage(int page) {  
        this.page = page;  
    }  
    public List getResultList() {
        return resultList;  
    }  
    public void setResultList(List resultList) {  
        this.resultList = resultList;  
    }  
    // 计算总页数  
    public void setTotalPages(int numPerPage,int totalRows) {  
        if (totalRows % numPerPage == 0) {  
            this.totalPages = totalRows / numPerPage;  
        } else {  
            this.totalPages = (totalRows / numPerPage) + 1;  
        }  
    }  
  
}  