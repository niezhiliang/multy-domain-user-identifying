package com.niezhiliang.controller;

import com.alibaba.fastjson.JSON;
import com.niezhiliang.domain.RespInfo;
import com.niezhiliang.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class IndexController {
  @Autowired
  private HttpSession session;
  @Autowired
  private HttpServletRequest request;

  /**
   * 登录接口
   * @param user
   * @return
   */
  @RequestMapping(value = "login")
  public String login(@RequestBody User user) {
    RespInfo respInfo = new RespInfo();

    if (user.getUsername().equals("admin")
      &&user.getPassword().equals("admin")) {

        respInfo.setCode(20);
        respInfo.setMsg("登录成功");
        respInfo.setData(session.getId());

    } else {

        respInfo.setCode(50);
        respInfo.setData(session.getId());
        respInfo.setMsg("账号或密码错误");
    }

    return JSON.toJSONString(respInfo);
  }


  /**
   * 请求接口1
   * @param num
   * @return
   */
  @RequestMapping(value = "index1")
  public String index1(String num) {
    RespInfo respInfo = new RespInfo();
    respInfo.setCode(20);
    Map<String,String> map = new HashMap<String, String>();
    map.put("num",num);
    map.put("path",request.getRequestURI());
    map.put("sessionid",session.getId());
    respInfo.setData(map);

    System.out.println(JSON.toJSONString(map));
    return JSON.toJSONString(respInfo);
  }

    /**
     * 请求接口2
     * @param num
     * @return
     */
  @RequestMapping(value = "index2")
  public String index2(String num){
      RespInfo respInfo = new RespInfo();
      respInfo.setCode(20);
      Map<String,String> map = new HashMap<String, String>();
      map.put("num",num);
      map.put("path",request.getRequestURI());
      map.put("sessionid",session.getId());
      respInfo.setData(map);

      System.out.println(JSON.toJSONString(map));
      return JSON.toJSONString(respInfo);
  }

    /**
     * 请求接口3
     * @param num
     * @return
     */
    @RequestMapping(value = "index3")
    public String index3(String num){
        RespInfo respInfo = new RespInfo();
        respInfo.setCode(20);
        Map<String,String> map = new HashMap<String, String>();
        map.put("num",num);
        map.put("path",request.getRequestURI());
        map.put("sessionid",session.getId());
        respInfo.setData(map);
        System.out.println(JSON.toJSONString(map));

        return JSON.toJSONString(respInfo);
    }

}
