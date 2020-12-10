let users
let roles

//Заполнение таблицы юзеров; параметр - масив объектов юзер
function updateUsersTable(users) {
    // debugger
    let userTable = document.getElementById("users-table")
    userTable.innerHTML = ""

    console.log(users)
    if (users.length > 0) {
        let tmp = ""
        users.forEach((user) => {
            tmp += "<tr id = " + user.id + ">"
            tmp += "<td>" + user.id + "</td>"
            tmp += "<td>" + user.username + "</td>"
            tmp += "<td>" + user.name + "</td>"
            tmp += "<td>" + user.lastName + "</td>"
            tmp += "<td>" + user.age + "</td>"
            tmp += "<td>"
            user.roleIds.forEach((id) => {
                roles.forEach((role) => {
                    if (role.id == id) {
                        tmp += role.name + " "
                    }
                })

            })
            tmp += "</td>"
            tmp += "<td>" +
                // "<button data-id=" + user.id + " type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#editModal\">" +
                '<button type="button" class="btn btn-info" data-toggle="modal" data-target="#editModal" data-whatever="' + user.id + '">'+

                "Edit" +
                "</button>" +
                "</td>" +
                "<td>" +
                // "<button data-id=" + user.id + " value=" + user.id + " type=\"button\" class=\"btn btn-danger my-delete-btn\">" +
                // data-toggle="modal" data-target="#deleteModal"
                '<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" data-whatever="' + user.id + '">'+
                "Delete" +
                "</button>" +
                "</td>" +
                "</tr>"
        })

        userTable.innerHTML = tmp
    }
}

//получение данных с сервера массива ролей и массива юзеров
async function getDataForTable() {
    let response = await fetch('/api/roles')
    roles = await response.json()
    console.log(roles)

    response = await fetch('/api/users')
    users = await response.json()
    console.log(users)
}

getDataForTable().then(() => {updateUsersTable(users)})

async function postData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrerPolicy: 'no-referrer', // no-referrer, *client
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    });
    return await response.json(); // parses JSON response into native JavaScript objects
}

// async function getUsers() {
//     let response = await fetch('/api/users')
//     users = await response.json()
// }

// function getRoles() {
//     fetch("/api/roles").then(
//         res => {
//             res.json().then(
//                 roles => {
//                     console.log(roles)
//                 }
//                 return roles
//             )
//         }
//     )
// }

// function getUsers() {
//     fetch("/api/users").then(
//         res => {
//             res.json().then(
//                 users => {
//                     console.log(users)
//                     return users
//                 }
//             )
//         }
//     )
// }

//Функция вешается на событие появления модалки бутстрапа
//делаем запрос данных юзера, чтобы ими заполнить форму
$('#editModal').on('show.bs.modal', async function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('whatever') // Extract info from data-* attributes

    //Дикий микс jquery и poor js
    let response = await fetch('/api/users/' + id)
    let user = await response.json()
    console.log(user)

    var modal = $(this)
    // modal.find('.modal-title').text('id = ' + id + '; username = ' + user.username)
    modal.find('#id-edit-form').val(user.id)
    modal.find('#username-edit-form').val(user.username)
    modal.find('#password-edit-form').val(user.password)
    modal.find('#name-edit-form').val(user.name)
    modal.find('#lastname-edit-form').val(user.lastName)
    modal.find('#age-edit-form').val(user.age)

    let tmp = '';

    roles.forEach((role) => {
        let checked = user.roleIds.toString().includes(role.id) ? `checked` : ``
        tmp += `<div class="form-check">
                    <input class="form-check-input" type="checkbox" name="roles" value="${role.id}"
                                id="defaultCheck${role.id}" ${checked}>
                    <label class="form-check-label" for="defaultCheck${role.id}">
                        ` + role.name + `
                    </label>
                </div>`

    })

    // let inputRoles = $('#my-form-role-group')
    // inputRoles.innerHTML = tmp
    $("#my-form-role-group").html(tmp)
})

$('#deleteModal').on('show.bs.modal', async function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('whatever') // Extract info from data-* attributes

    //Дикий микс jquery и poor js
    let response = await fetch('/api/users/' + id)
    let user = await response.json()
    console.log(user)

    var modal = $(this)
    // modal.find('.modal-title').text('id = ' + id + '; username = ' + user.username)
    modal.find('#id-delete-form').val(user.id)
    modal.find('#username-delete-form').val(user.username)
    modal.find('#password-delete-form').val(user.password)
    modal.find('#name-delete-form').val(user.name)
    modal.find('#lastname-delete-form').val(user.lastName)
    modal.find('#age-delete-form').val(user.age)

})

$('#btn-modal-edit-submit').on('click', async function() {

    let user = {
        name: "Rogers888",
        lastName: "Str555ike",
        age: 2,
        username: "kop",
        password: "123",
        roleIds: [
        1,
        2
    ]
    }

    // действия, которые будут выполнены при наступлении события...
    console.log($(this).text())
    console.log("=====++=====")
    console.log(user)

    let data = await postData("/api/users", user)
    console.log(data); // JSON data parsed by `response.json()` call

    getDataForTable()
        .then(() => {updateUsersTable(users)})
        .then(() => {$('#editModal').modal('hide')})

});


function getUserFormForm(form) {
    let user = {};
    user.id = form.id
    user.name = form.name
    user.lastName = form.lastName
    user.password = form.password
    user.username = form.username
    user.age = form.age
    user.roleIds = form.roleIds
    return user
}

// const buttonDelete = $(".my-delete-btn");
// buttonDelete.click(
//     function () {
//         $.ajax("/api/users/" + $(this).attr("value"), {
//             method: "DELETE",
//             // data: {id: $(this).attr("value")}, //в rest-контроллер будет передан id=1 (см. value из тэга button выше)
//             dataType: "text",
//             success: function (msg) {
//                 $("#users")
//                     .find("#" + msg) //ищем div с id=1
//                     .remove();
//
//             }
//         })
//     }
// )

