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

//Заполенение области чекбоксов ролей в форме,
//Аргумент roleIds - массив айдишников юзера, чтобы отметить роли, которые у него есть
function setFormRoles(roleIds) {

    let tmp = ''

    roles.forEach((role) => {
        let checked = roleIds.toString().includes(role.id) ? `checked` : ``
        tmp += `<div class="form-check">
                    <input class="form-check-input" type="checkbox" name="roles" value="${role.id}"
                                id="defaultCheck${role.id}" ${checked}>
                    <label class="form-check-label" for="defaultCheck${role.id}">
                        ` + role.name + `
                    </label>
                </div>`

    })
    return tmp
}

//Заполеняем форму мадалки данными юзера
function setDataToForm(elem, user) {

    // modal.find('.modal-title').text('id = ' + id + '; username = ' + user.username)
    elem.find('input[name="id"]').val(user.id)
    elem.find('input[name="username"]').val(user.username)
    elem.find('input[name="password"]').val(user.password)
    elem.find('input[name="name"]').val(user.name)
    elem.find('input[name="lastname"]').val(user.lastName)
    elem.find('input[name="age"]').val(user.age)
    let tmp = setFormRoles(user.roleIds)

    elem.find(".my-form-role-group").html(tmp)
}

//получаем списки юзеров и всех возможных ролей
// и заполняем глобальные переменные users и roles
async function getDataForTable() {
    let response = await fetch('/api/roles')
    roles = await response.json()
    console.log(roles)

    response = await fetch('/api/users')
    users = await response.json()
    console.log(users)
}

//заполняем переменные а затем заполняем данными главную таблицу юзеров
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

async function putData(url = '', data = {}) {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
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

async function deleteData(url = '') {
    // Default options are marked with *
    const response = await fetch(url, {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
    });
    return await response.text(); // parses JSON response into native JavaScript objects
}

function setDataToModal(action) {
    $(`#${action}Modal`).on('show.bs.modal', async function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id = button.data('whatever') // Extract info from data-* attributes

        //Дикий микс jquery и poor js
        let response = await fetch('/api/users/' + id)
        let user = await response.json()
        console.log(user)

        var modal = $(this)
        modal.find('fieldset').prop('disabled', action == "delete")
        modal.find('input[name="password"]').prop('type', (action == "delete") ? "hidden" : "password")
        // modal.find('.modal-title').text('id = ' + id + '; username = ' + user.username)

        setDataToForm(modal, user)
    })
}

setDataToModal("edit")
setDataToModal("delete")

$('#btn-modal-edit-submit').on('click', async function() {
    // действия, которые будут выполнены при наступлении события...

    let form = this.closest('.modal').querySelector('form')
    const {id, name, lastname, username, password, age, roles} = form;

    let rolesArr = []
    roles.forEach((role)=>{
        if (role.checked) {
            rolesArr.push(role.value)
        }
    })

    let user = {
        id: id.value,
        name: name.value,
        lastName: lastname.value,
        age: age.value,
        username: username.value,
        password: password.value,
        roleIds: rolesArr,
    }

    console.log($(this).text()) //todo del logs
    console.log("=====++=====")
    console.log(user)

    let data = await putData("/api/users", user)
    console.log(data); // JSON data parsed by `response.json()` call

    getDataForTable()
        .then(() => {updateUsersTable(users)})
        .then(() => {$('#editModal').modal('hide')})

});

$('#btn-new-user-submit').on('click', async function() {
    // действия, которые будут выполнены при наступлении события...

    let form = this.closest('.new-user-form').querySelector('form')
    const {name, lastname, username, password, age, roles} = form;

    let rolesArr = []

    roles.forEach((role)=>{ //todo ++++ roles list
        if (role.checked) {
            rolesArr.push(role.value)
        }
    })

    let user = {
        name: name.value,
        lastName: lastname.value,
        age: age.value,
        username: username.value,
        password: password.value,
        roleIds: rolesArr,
    }

    console.log($(this).text()) //todo del logs
    console.log("=====++=====")
    console.log(user)

    let data = await postData("/api/users", user)
    console.log(data); // JSON data parsed by `response.json()` call

    getDataForTable()
        .then(() => {updateUsersTable(users)})
        .then(() => {
            $('#users').tab('show')
        }) // смена вкладки на таблицу юзеров

});

$('#btn-modal-delete-submit').on('click', async function() {
    // действия, которые будут выполнены при наступлении события...
    console.log($(this).text())

    let id = document.querySelector("div#deleteModal input[name=id]")
    console.log(id)

    let data = await deleteData(`/api/users/${id.value}`)
    console.log(data); // JSON data parsed by `response.json()` call

    getDataForTable()
        .then(() => {updateUsersTable(users)})
        .then(() => {$('#deleteModal').modal('hide')})

});

$('a[data-toggle="tab"]').on('shown.bs.tab', async function (e) {
    // debugger
    // e.target // newly activated tab
    // e.relatedTarget // previous active tab

    await getDataForTable()


    let rolesArr = []
    roles.forEach((role) => {
        if(role.name == 'ROLE_USER') {
            rolesArr.push(role.id)
        }
    })

    if (e.target.id == "new-user-tab"){
        setDataToForm($('#new'), {//todo ??? захардкоженный объект. Как делать так чтобы не зависить от изменения модели на сервере ???
            id: "",
            name: "",
            lastName: "",
            username: "",
            password: "",
            age: "",
            roleIds: rolesArr
        })
    } else if (e.target.id == "users-table-tab") {
        updateUsersTable(users)
    }
})

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

//Функция вешается на событие появления модалки бутстрапа
//делаем запрос данных юзера, чтобы ими заполнить форму
// $('#editModal').on('show.bs.modal', async function (event) {
//     var button = $(event.relatedTarget) // Button that triggered the modal
//     var id = button.data('whatever') // Extract info from data-* attributes
//
//     //Дикий микс jquery и poor js
//     let response = await fetch('/api/users/' + id)
//     let user = await response.json()
//     console.log(user)
//
//     var modal = $(this)
//     modal.find('fieldset').prop('disabled', false)
//
//     setDataToModalForm(modal, user)
// })



