/**
 * FileName: CommonResponse
 * Author:   liluming
 * Date:     2020/4/14 10:30 上午
 * Description:
 */
package com.gome.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -7036658567937264964L;

    private boolean success = true;
    private Integer code = 0;
    private String message = "成功";
    private T data;

    @JsonInclude(Include.NON_NULL)
    private String debug;

    public boolean isSuccess() {
        return ((getCode() == 0) && (this.data != null));
    }

    public CommonResponse() {
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    /**
     * 处理普通数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResponse<T> successResult(T data) {
        CommonResponse<T> result = new CommonResponse<T>();
        result.data = data;
        return result;
    }

    /**
     * 处理分页数据
     *
     * @param data       数据列表
     * @param pageInfo   分页信息
     * @param permission 权限列表
     * @param <T>
     * @return
     */
    public static <T> CommonResponse<T> successResult(T data, T pageInfo, T permission) {
        CommonResponse<T> result = new CommonResponse<T>();
        Map map = new HashMap<>();
        map.put("recordList", data);
        map.put("pageInfo", pageInfo);
        map.put("permission", permission);
        result.data = (T) map;
        return result;
    }

    /**
     * 处理分页数据
     *
     * @param data     数据列表
     * @param pageInfo 分页信息
     * @param <T>
     * @return
     */
    public static <T> CommonResponse<T> successResult(T data, T pageInfo) {
        CommonResponse<T> result = new CommonResponse<T>();
        Map map = new HashMap<>();
        map.put("recordList", data);
        map.put("pageInfo", pageInfo);
        result.data = (T) map;
        return result;
    }

}
