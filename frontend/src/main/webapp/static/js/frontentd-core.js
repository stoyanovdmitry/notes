const REST_URL = 'http://localhost:8080/rest/rest';

var username = 'user';
var password = 'pass';


const headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Accept', 'application/json');
headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));

const userOnLoad = new Vue({
    el: '#app',
    data: {
        user: null
    },
    mounted: function () {

        const app = this;

        fetch(REST_URL + '/users/' + username, {
            method: 'GET',
            headers: headers
        })
            .then(function (response) {
                if (response.status === 200)
                    return response.json()
                        .then(function (user) {
                            app.user = user;

                            fetch(REST_URL + '/users/' + username + '/notes', {
                                method: 'GET',
                                headers: headers
                            })
                                .then(function (response) {
                                    if (response.status === 200)
                                        return response.json()
                                        // .then(notes => app.notes = notes);
                                            .then(notesVue.loadNotes);
                                    else
                                        alert(response.status);
                                })
                        });
                else
                    alert(response.status + '\n' + response.message)
            })
            .catch(function (reason) {
                alert(reason)
            });
    },
});


const notesVue = new Vue({
    el: '#app2',
    data: {
        notes: []
    },
    methods: {
        loadNotes: function () {

            const app = this;

            fetch(REST_URL + '/users/' + username + '/notes', {
                method: 'GET',
                headers: headers
            })
                .then(function (response) {
                    if (response.status === 200)
                        return response.json()
                            .then(notes => app.notes = notes.reverse());
                    else
                        alert(response.status);
                });
        },
        deleteNote: function (note) {

            const app = this;

            fetch(REST_URL + '/users/' + username + '/notes/' + note.id, {
                method: 'DELETE',
                headers: headers
            })
                .then(response => {
                    if (response.status === 200) {
                        const index = app.notes.indexOf(note);
                        app.notes.splice(index, 1);
                    } else
                        alert(response.status);
                });
        },
        saveNote: function (noteText) {

            const app = this;

            const bodyData = JSON.stringify({text: noteText})

            fetch(REST_URL + '/users/' + username + '/notes', {
                method: 'POST',
                headers: headers,
                body: bodyData
            })
                .then(response => {
                    if (response.status === 200) {
                        app.loadNotes(); //would be great to optimize this part of code, maybe
                    } else alert(response.status);
                });
        }
    },
});

// const saveNoteVue = new Vue({
//     el: '#app3',
//     methods: {
//         saveNote: function (noteText) {
//
//
//             fetch(REST_URL + '/users/' + username + '/notes', {
//                 method: 'POST',
//                 headers: headers,
//                 body: {
//                     'text': 'sa'
//                 }
//             })
//                 .then(response => {
//                     if (response.status !== 200)
//                         alert(response.status);
//                 });
//         }
//     }
// });
