<template>
  <v-container>
    <v-container>
      <v-file-input v-model="file" show-size multiple label="파일 업로드"></v-file-input>
      <v-btn @click="upload" color="primary">S3에 파일 업로드</v-btn>
    </v-container>

    <v-container>

      <v-card>
        <v-card-title>AWS S3 저장목록</v-card-title>
      <v-data-table
        :headers="headers"
        :items="storage"
        :page.sync="page"
        :items-per-page="itemsPerPage"
        :loading="loading"
        hide-default-footer
        class="elevation-1"
        @page-count="pageCount = $event"
      >
      <template v-slot:item="row">
        <tr>
          <td>{{row.item.title}}</td>
          <td>{{row.item.fileSize}}</td>
          <td>{{row.item.lastModified}}</td>
          <td>
            <v-btn text small :href="'http://localhost:5000/file?title='+row.item.title">
              다운로드
            </v-btn>
          </td>
          <td>
            <v-btn text small color="error" @click="deleteFile(row.item.title)">
              삭제
            </v-btn>
          </td>
        </tr>
      </template>
      </v-data-table>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
      </v-card>
    </v-container>
    
  </v-container>
</template>

<script>
import axios from 'axios'

  export default {
    el: '#app',
    data () {
      return {
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        headers: [
          { text: '이름', align: 'start', sortable: true, value: 'title' },
          { text: '크기', value: 'fileSize' },
          { text: '수정날짜', value: 'lastModified' },
          { text: '다운로드', value: ''},
          { text: '삭제', value: ''},
        ],
        storage: [],
        file: [],
      }
    },
    mounted() {
      axios.get("http://localhost:5000/")
      .then((res) => {
          this.storage = res.data
      }).catch((e)=> {
          console.log(e)
      })
    },
    methods:{
      async upload() {
        var formData = new FormData();
        formData.append('file', this.file[0])
        await axios.post('http://localhost:5000/file',
          formData,
          {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        ).then(function(){
          console.log('successed');
          location.reload();
        }).catch(function(err){
          console.log('failed -> ' + err );
        });
      },

      deleteFile(key){
        const params = {
          title: key,
        };
        axios.delete('http://localhost:5000/file',
          {
            params
          },
        ).then(function(res){
          console.log('successed -> ' + res);
          location.reload();
        }).catch(function(err){
          console.log('failed -> ' + err);
        });
      }


    }
  }
</script>
