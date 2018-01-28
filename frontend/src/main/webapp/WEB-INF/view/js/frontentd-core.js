const REST_URL = 'http://localhost:8080/rest/rest';

var username = 'user';
var password = 'pass';


var headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Accept', 'application/json');
headers.append('Authorization', 'Basic ' + btoa('user:pass'));

var userOnLoad = new Vue({
    el: '#app',
    data: {
        user: null
    },
    mounted: function () {

        var app = this;


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

            var app = this;

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
        deleteNote: function (id) {

            // fetch(REST_URL + '/users')
        }
    },
});

// var
