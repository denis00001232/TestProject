<template>
  <div class="container">
    <input placeholder="Zoom" v-model="zoom"/>
    <input placeholder="x" v-model="xCord"/>
    <input placeholder="y" v-model="yCord"/>
    <button @click="loadTile(zoom, xCord, yCord)">Получить тайл</button>
  </div>
  <div class="container">
    <div
        class="file-uploader"
        @dragover.prevent
        @drop.prevent="handleDrop">
      <input type="file" @change="handleFileChange"/>
      <p v-if="file">📁 Выбран файл: {{ file.name }}</p>
    </div>
  </div>
  <div class="container">
    <button class="btn" @click="uploadFile">Отправить новый mbtiles</button>
  </div>
  <img class="img" :src="image">
</template>

<script>
import axios from "axios";
import ip from "@/ip";

export default {
  data() {
    return {
      zoom:'',
      xCord:'',
      yCord:'',
      file: null,
      image: null,
    }
  },
  methods: {
    handleFileChange(event) {
      this.file = event.target.files[0];
    },
    loadTile(zoom, xCord, yCord) {

      axios.get(ip.ip + '/get_tile', {
        params: { zoom, xCord, yCord },
        responseType: 'blob' // важно для получения изображения
      })
          .then(response => {
            this.image = URL.createObjectURL(response.data)
          })
          .catch(error => {
            console.error('Ошибка загрузки изображения:', error)
          })
    },
    uploadFile() {
      if (this.file == null || this.file.name == "") {
        return;
      }

      const formData = new FormData();
      formData.append("file", this.file);
      axios.post(ip.ip + '/upload', formData)
          .then(response => {
            console.log("Успех:", response.data);
          })
          .catch(error => {
            console.error("Ошибка:", error.response ? error.response.data : error);
          });
    }
  }
}
</script>

<style>
.container {
  display: flex;
  flex-direction: row;

}

.file-uploader {
  margin: 15px;
  border: 2px dashed #999;
  width: 400px;
  padding: 2rem;
  text-align: center;
  border-radius: 10px;
  background-color: #f5f5f5;
  cursor: pointer;
}

.btn {
  max-height: 50px;
}

.img {
  height: 400px;
  width: 400px;
  border: 1px solid #999;

}
</style>
