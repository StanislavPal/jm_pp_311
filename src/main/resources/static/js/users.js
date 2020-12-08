let users
let roles

//получение данных с сервера массива ролей и массива юзеров
async function getResponse() {
    let response = await fetch('/api/roles')
    roles = await response.json()
    console.log(roles)

    response = await fetch('/api/users')
    users = await response.json()
    console.log(users)

    updateUsersTable(users)
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

//Заполнение таблицы юзеров; параметр - масив объектов юзер
function updateUsersTable(users) {
    // debugger
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
                roles.forEach((role) =>{
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
        document.getElementById("users-table").innerHTML = tmp
    }
}

//Функция вешается на событие появления модалки бутстрапа
//делаем запрос данных юзера, чтобы ими заполнить форму
$('#editModal').on('show.bs.modal', async function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('whatever') // Extract info from data-* attributes
    let response = await fetch('/api/users/' + id)
    let user = await response.json()
    console.log(user)
    var modal = $(this)
    modal.find('.modal-title').text('id = ' + id + '; username = ' + user.username)
    modal.find('.modal-body input').val(user.lastName)
})

getResponse()

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

