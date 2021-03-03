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
      ['My favorite color is purple.', 'I am Hispanic but can not speak Spanish. ', '🥳 I was born in June!', 'My most used emojis: 🥰🥺😈❤️'];

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

    const servletContainer = document.getElementById('servlet-container');
    servletContainer.innerText = textFromResponse;
}

async function getServerStats(){
    //fetch function that will get info from /hello
    const serverResponse = await fetch('/my-data-url');

    //parsing to json
    const info = await serverResponse.json();
    const infoList = document.getElementById('hello-container');
  infoList.innerHTML = '';

    infoList.appendChild(
        createListElement('Hello: ' + infoList)
    )

}

