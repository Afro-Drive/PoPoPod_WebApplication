'use strict';

console.log('musicPlayer.js was loaded!');

/*************************
 * defined field
 *************************/
const ALLOW_EXTENSIONS = new Array('mp3', 'mp4', 'mpeg', 'wav', 'ogg');

// pick up user's requested music
let inputMusic = document.getElementsByClassName('reqSound')[0];

inputMusic.addEventListener("change", function(event) {
    const files = event.target.files; //取得したファイル群を配列で取り出す
    const name = files[0].name;
    checkFileExtension(name);
},false);

/************************
 * defined function
 ************************/

/**
 * check file extension
 * @param {String} filename to check the extension
 * @returns {void}
 */
function checkFileExtension(filename) {
    const extension = getFileExtension(filename).toLowerCase();
    if(extension === '' || ALLOW_EXTENSIONS.indexOf(extension) === -1) {
        alert(`This file is not supported format[filename:${filename}]. Please select file again.`);
    }
}

/**
 * get file extension
 * @param {String} filename to check the extension
 * @returns {String} file extension or empty string if not exist period word
 */
function getFileExtension(filename) {
    let pos = filename.lastIndexOf('.');
    if(pos === -1) return '';
    return filename.slice(pos + 1);
}