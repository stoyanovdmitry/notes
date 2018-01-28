const REST_URL = 'http://localhost:8080/rest/rest';

var username = 'user';
var password = 'pass';


var headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Accept', 'application/json');
headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));

var userOnLoad = new Vue({
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
                                            .then(notesVue.getNotes);
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


var notesVue = new Vue({
    el: '#app2',
    data: {
        notes: []
    },
    methods: {
        getNotes: function () {

            const app = this;

            fetch(REST_URL + '/users/' + username + '/notes', {
                method: 'GET',
                headers: headers
            })
                .then(function (response) {
                    if (response.status === 200)
                        return response.json()
                            .then(notes => app.notes = notes);
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
                    if(response.status === 200) {
                        alert(response.status);
                        var index = app.notes.indexOf(note);
                        app.notes.splice(index, 1);
                    } else
                        alert(response.status);
                });
        }
    },
});

// var
