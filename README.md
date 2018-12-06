# multy-domain-user-identifying
前后端分离跨域每次请求sessionId不一致解决 java+vue+axios

####  随着前端框架的不断发展，越来越多的项目更喜欢用前段后分离来做，作为后台人员，完全不想写一点前端，只想关注API的编写就够了 哈哈

但前后端分离开发项目还是会踩到太多坑，最近就有一个需求是，多个站点共享用户登录信息，就是当用户在某一个站点登录了，当点击到另一个项目的站点时，延用之前的登录信息，无需再次登录。 此时就想到了 使用Redis来实现该需求，当用户登录成功后，将用户信息保存到Redis，以sessionId为key json格式的用户信息为value。
这样做 那就有一个要求，那就是必须保持每次请求的sessionId必须一致。
前端使用的是vue2.0 后台使用的是java  springboot


![演示gif](https://github.com/niezhiliang/multy-domain-user-identifying/blob/master/imgs/demo.gif)

- 首先我们解决跨域问题
```java
@Configuration
public class CorsConfig {
  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(true);
    return corsConfiguration;
  }
  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildConfig());
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}

```
- 然后写好controller代码
```java
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
```

#### 关键  在main.js下面加上这句代码

`axios.defaults.withCredentials = true`

- 我们看前端页面的代码
```javascript
<template>
  <div>
    <h2>前后端分离SessionId共享(Java + Vue)</h2>
    <Card>
      <div v-for="obj in sessionids">
        <span>第{{ obj.num}}次访问</span>
        <span style="color: red">{{ obj.path }}</span>
        <span>SessionId:<b>{{ obj.sessionid }}</b></span>
      </div>
    </Card>
  </div>
</template>
<script>
  import http from './http'
  export default {
    data () {
      return {
        sessionids: []
      }
    },
    created() {
      this.getSessionId(1)
    },
    methods: {
      getSessionId: function (number) {
        let timer = setInterval(() => {
          http.get('index1',{num: number}).then((res)=> {
            if (res) {
              console.log(res.data)
              this.sessionids.push(res.data)
            }
          })
          http.get('index2',{num: number}).then((res)=> {
            if (res) {
              console.log(res.data)
              this.sessionids.push(res.data)
            }
          })
          http.get('index3',{num: number}).then((res)=> {
            if (res) {
              console.log(res.data)
              this.sessionids.push(res.data)
            }
          })
          if (number >= 5) {
            clearInterval(timer)
          }
          number++
        },2000);

      }
    }
  }
</script>

```
[源代码地址：https://github.com/niezhiliang/multy-domain-user-identifying](https://github.com/niezhiliang/multy-domain-user-identifying)
