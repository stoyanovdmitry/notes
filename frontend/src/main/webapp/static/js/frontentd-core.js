const REST_URL = 'http://localhost:8080/rest/rest';

var username = 'user';
var password = 'pass';


const headers = new Headers();
headers.append('Content-Type', 'application/json');
headers.append('Accept', 'application/json');
headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));


$(document).ready(function () {

    $('#addNoteModal').on('shown.bs.modal', function () {
        $('#addNoteModalTextarea').trigger('focus')
    });

    $('#editNoteModal').on('shown.bs.modal', function () {
        $('#editNoteModalTextarea').trigger('focus')
    });
});

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
                            notesVue.loadNotes();
                        });
                else alert(response.status)
            })
            .catch(function (reason) {
                console.log('Load user is failed:\n' + reason);
            });
    },
});


const notesVue = new Vue({
    el: '#app2',
    data: {
        notes: [],
        noteText: '',
        editableNote: null,
        editableIndex: -1,
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
                    else alert(response.status);
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
                    } else alert(response.status);
                });
        },
        saveNote: function (noteText) {

            const app = this;
            const bodyData = JSON.stringify({text: noteText});

            fetch(REST_URL + '/users/' + username + '/notes', {
                method: 'POST',
                headers: headers,
                body: bodyData
            })
                .then(response => {
                    if (response.status === 200) {
                        app.loadNotes(); //would be great to optimize this part of code, maybe

                        $('#addNoteModal').modal('hide');
                        app.noteText = '';
                    } else alert(response.status);
                });
        },
        setEditableNote: function (note) {

            // this.editableNote = note;
            this.editableNote = {
                id: note.id,
                text: note.text,
                user: note.user
            };

            // alert(this.editableNote == note);
            this.editableIndex = this.notes.indexOf(note);
        },
        updateNote: function () {

            const app = this;
            const note = app.editableNote;
            const bodyData = JSON.stringify(note);

            fetch(REST_URL + '/users/' + username + '/notes/' + app.editableNote.id, {
                method: 'PUT',
                headers: headers,
                body: bodyData
            })
                .then(response => {
                    if (response.status === 200) {
                        app.notes[app.editableIndex] = app.editableNote;

                        $('#editNoteModal').modal('hide');

                        app.editableNote = null;
                        app.editableIndex = -1;

                    } else alert(response.status);
                });
        }
    },
});