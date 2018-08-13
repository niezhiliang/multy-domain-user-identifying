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
