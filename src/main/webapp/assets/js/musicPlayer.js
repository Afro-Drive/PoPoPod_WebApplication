'use strict';

/*************************
 * defined field
 *************************/
const ALLOW_EXTENSIONS = new Array('mp3', 'mp4', 'mpeg', 'wav', 'ogg');
const EVENT_CHANGE = 'change';
const EVENT_DRAGOVER = 'dragover';
const EVENT_DRAGLEAVE = 'dragleave';
const EVENT_DROP = 'drop';

let inputMusic = document.getElementById('file-drop-area');
let audio = document.getElementsByTagName('audio')[0];

// pick up and check user's requested music
inputMusic.addEventListener(EVENT_CHANGE, function(event) {
    console.log(inputMusic);
    console.log(inputMusic.files);
    const dataTransfer = getCorrectFilesDataTransfer(inputMusic.files);
    if(dataTransfer.files.length) {
        console.log('dataTransfer: ' + dataTransfer);
        inputMusic.files = dataTransfer.files;
        let form = inputMusic.parentElement;
        form.submit();
    }
},false);

// add the event when music file drags over and leaves
//fileDropArea.addEventListener(EVENT_DRAGOVER, function(event) {
//    fileDropArea.classList.add('drag-area-active');
//    event.stopPropagation();
//},false);
//
//fileDropArea.addEventListener(EVENT_DRAGLEAVE, function(event) {
//    fileDropArea.classList.remove('drag-area-active');
//    event.stopPropagation();
//},false);

// 動作しないため、要修正
//fileDropArea.addEventListener(EVENT_DROP, function(event) {
//    const files = event.target.files; //取得したファイル群を配列で取り出す
//    const name = files[0].name;
//    console.log('drop file');
//    checkFileExtension(name);
//    
//    event.preventDefault();
//},false);

/************************
 * defined function
 ************************/

/**
 * check file extension
 * @param {String} filename to check the extension
 * @returns {bool}
 */
function checkFileExtension(filename) {
    console.log(filename);
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
 * get DataTransfer from requested file list
 * @param {FileList} files to check the extension
 * @returns {DataTransfer} DataTransfer object having correct format files in own fileList
 */
function getCorrectFilesDataTransfer(files) {
    const dataTransfer = new DataTransfer();
    for(let file of files) {
        if(file && checkFileExtension(file.name)) {
            dataTransfer.items.add(file);
        }
    }
    return dataTransfer;
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

/**
 * the event handler when any file(s) is dropped from OS file system
 * @param {Event} e event object
 * @returns {void}
 */
function dropHandler(e) {
    e.preventDefault();

    let dataTransfer = getCorrectFilesDataTransfer(e.dataTransfer.files);
    if(dataTransfer.files.length) {
        inputMusic.files = dataTransfer.files;
        let form = e.target.firstElementChild;
        form.submit();
    }
}

/**
 * the event handler when the file(s) from OS file system enter the drop zone 
 * @param {Event} e Event object
 * @returns {void}
 */
function dragOverHandler(e) {
    console.log('File(s) enter drop zone.');

    e.preventDefault();
    inputMusic.classList.add('drag-area-active');
}

function dragLeaveHandler(e) {
    e.preventDefault();
    inputMusic.classList.remove('drag-area-active');
}