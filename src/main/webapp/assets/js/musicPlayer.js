'use strict';

/*************************
 * defined field
 *************************/
const ALLOW_EXTENSIONS = new Array('mp3', 'mp4', 'mpeg', 'wav', 'ogg');
const EVENT_CHANGE = 'change';
const EVENT_DRAGOVER = 'dragover';
const EVENT_DRAGLEAVE = 'dragleave';
const EVENT_DROP = 'drop';

let inputMusic = document.getElementsByClassName('reqSound')[0];
let fileDragArea = document.getElementById('file-drag-area');
let audio = document.getElementsByTagName('audio')[0];

// pick up and check user's requested music
inputMusic.addEventListener(EVENT_CHANGE, function(event) {
    const files = event.target.files; //取得したファイル群を配列で取り出す
    const name = files[0].name;
    if(checkFileExtension(name)) {
        let form = fileDragArea.firstElementChild;
        form.submit();
//        playSelectMusic(files[0]);
    }
},false);

// add the event when music file drags over and leaves
fileDragArea.addEventListener(EVENT_DRAGOVER, function(event) {
    fileDragArea.classList.add('drag-area-active');
    event.stopPropagation();
},false);

fileDragArea.addEventListener(EVENT_DRAGLEAVE, function(event) {
    fileDragArea.classList.remove('drag-area-active');
    event.stopPropagation();
},false);

// 動作しないため、要修正
fileDragArea.addEventListener(EVENT_DROP, function(event) {
    const files = event.target.files; //取得したファイル群を配列で取り出す
    const name = files[0].name;
    checkFileExtension(name);
    
    event.preventDefault();
},false);

/************************
 * defined function
 ************************/

/**
 * check file extension
 * @param {String} filename to check the extension
 * @returns {bool}
 */
function checkFileExtension(filename) {
    const extension = getFileExtension(filename).toLowerCase();
    if(extension === '' || ALLOW_EXTENSIONS.indexOf(extension) === -1) {
        alert(`This file is not supported format[filename:${filename}]. Please select file again.`);
        return false;
    }
    return true;
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

/**
 * set the audio element the music file and start playing
 * @param {File} musicFile
 * @returns {void}
 */
function playSelectMusic(musicFile) {
    let audioSource = document.getElementById('audioSource');
    const filename = musicFile.name;
    audioSource.setAttribute('src', filename);
    audio.load();
    audio.play();
}