'use strict';

/*************************
 * defined field
 *************************/

const ALLOW_EXTENSIONS = new Array('mp3', 'mp4', 'mpeg', 'wav', 'ogg');

/*************************
 * Application URL Root Path
 **************************/
const APP_ROOT_PATH = "http://localhost:8084/PoPoPod_Web/";
const ASSETS_PATH = "assets/";
const SOUND_PATH = "sound/";
const SLASH = "/";

// Event Names
const EVENT_CHANGE = 'change';
const EVENT_DRAGOVER = 'dragover';
const EVENT_DRAGLEAVE = 'dragleave';
const EVENT_DROP = 'drop';
const EVENT_CLICK = 'click';
const EVENT_AUDIO_ENDED = 'ended';

let inputMusic = document.getElementById('file-drop-area');
let audio = document.getElementsByTagName('audio')[0];
let audioQueue = document.getElementById('audio-queue');

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

// initialize audio element
initAudioElement(audio);

// generate DOM Element and attach to audio-queue element
if(audioQueue.childElementCount > 0) {
    designDetailsQueue(audioQueue);
}

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
        let form = e.target.parentElement;
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

/**
 * add the function swapping playing music of requested queue, to audio-queue element
 * @param {Node} audioQueue
 * @returns {void}
 */
function designDetailsQueue(audioQueue) {
    let children = audioQueue.children;

    for(let childElem of children) {
        if(childElem.tagName.toLowerCase() === 'ol') {
            for(let liElem of childElem.children) {
                liElem.addEventListener(EVENT_CLICK, switchOtherMusic);
                if(liElem === childElem.firstElementChild) {
                    liElem.classList.add('playing-queue');
                }
            }
        }
    }
}

/**
 * Switch other music selected by user
 * @param {Event} e event object to select other music asset
 * @returns {void}
 */
function switchOtherMusic(e) {
    let musicName = e.target.textContent;
    loadMusic(musicName);
}

/**
 * Load music to audio element
 * @param {String} musicName to load music file name
 * @returns {void}
 */
function loadMusic(musicName) {
    audio.firstElementChild.src = APP_ROOT_PATH + ASSETS_PATH + SOUND_PATH + musicName;
    audio.load();
}

function play(musicName) {
    loadMusic(musicName);
    audio.play();
}
/**
 * Initialize HTMLaudioElement. Add Event, class.
 * @param {HTMLAudioElement} audioElem
 * @returns {void}
 */
function initAudioElement(audioElem) {
    audioElem.addEventListener(EVENT_AUDIO_ENDED, autoPlayNextQueue);
}
/**
 * Automatically load next queue music
 * 
 * <p>If playing music is the tail of requested queue, do nothing.</p>
 * @param {AudioProcessingEvent} audioEve
 * @returns {void}
 */
function autoPlayNextQueue(audioEve) {
    const queueList = Array.prototype.slice.call(audioQueue.children)
            .find(child => child.tagName.toLowerCase() === 'ol')
            .children;
    const nextIndex = Array.prototype.slice.call(queueList)
            .findIndex(item => item.classList.contains('playing-queue')) + 1;

    if(nextIndex < queueList.length) {
        const nextMusicName = queueList[nextIndex].textContent;

        play(nextMusicName);
    }
}
