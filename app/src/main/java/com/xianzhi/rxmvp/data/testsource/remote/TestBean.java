package com.xianzhi.rxmvp.data.testsource.remote;

import java.util.List;

/**
 * Created by yaocui on 2017/9/20.
 */

public class TestBean {

    /**
     * count : 32
     * next : null
     * previous : http://www.cuiyao.top/api/commit/?format=json
     * results : [{"name":"11123141414","email":"1123142414@131.com","phonenum":"18310312885","message":"3124125151515"},{"name":"11123141414","email":"1123142414@131.com","phonenum":"18310312885","message":"3124124"},{"name":"11123141414","email":"123124@1111","phonenum":"18310312885","message":"1231412414"},{"name":"11123141414","email":"333@add","phonenum":"123141241244","message":"125151121313"},{"name":"11123141414","email":"1123142414@131.com","phonenum":"18310312885","message":"31241414"},{"name":"11123141414","email":"444444@qq.com","phonenum":"18310312885","message":"1234"},{"name":"123","email":"1123142414@131.com","phonenum":"13124","message":"312412415"},{"name":"11123141414","email":"444444@qq.com","phonenum":"18310312885","message":"312414515"},{"name":"閫楁瘮鍦�","email":"123@kengni.com","phonenum":"13823336666","message":"鑿滈浮"},{"name":"宕斿灇","email":"592425690@qq.com","phonenum":"18310312885","message":"娴嬭瘯"},{"name":"鍒犲簱","email":"a841274635a@sohu.com","phonenum":"15984441221","message":"shibushisha"},{"name":"宕斿灇","email":"592425690@qq.com","phonenum":"18310312885","message":"娴嬭瘯"}]
     */

    private int count;
    private Object next;
    private String previous;
    private List<ResultsEntity> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        /**
         * name : 11123141414
         * email : 1123142414@131.com
         * phonenum : 18310312885
         * message : 3124125151515
         */

        private String name;
        private String email;
        private String phonenum;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
