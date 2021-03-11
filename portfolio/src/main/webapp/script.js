// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

//function for servlets
async function showSomething(){
    const responseFromServer = await fetch('/hello');
    const textFromResponse = await responseFromServer.text();

    const servletContainer = document.getElementById('servlet1-container');
    servletContainer.innerText = textFromResponse;
}

async function getRandomThoughts(){
    //fetch function that will get info from /hello
    const serverResponse = await fetch('/hello');

    //parsing to json
    const ranInfo = await serverResponse.json();
    //gets a random element w/Math.random()
    const ranElement = ranInfo[Math.floor(Math.random()*ranInfo.length)];  

    //connecting with index.html to print array of random info
    const servletContainer = document.getElementById('servlet2-container');
    servletContainer.innerText = ranElement;
}