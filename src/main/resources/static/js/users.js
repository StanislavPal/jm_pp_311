let users
let roles

function getRoles() {
    fetch("/api/roles").then(
        res => {
            res.json().then(
                roles => {
                    console.log(roles)
                    return roles
                }
            )
        }
    )
}

function getUsers() {
    fetch("/api/users").then(
        res => {
            res.json().then(
                users => {
                    console.log(users)
                    return users
                }
            )
        }
    )
}

function updateUsersTable(users) {
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
                tmp += id + " "
            })
            tmp += "</td>"
            tmp += "<td>" +
                "<button data-id=" + user.id + " type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#editModal\">" +
                "Edit" +
                "</button>" +
                "</td>" +
                "<td>" +
                "<button data-id=" + user.id + " value=" + user.id + " type=\"button\" class=\"btn btn-danger my-delete-btn\">" +
                // data-toggle="modal" data-target="#deleteModal"
                "Delete" +
                "</button>" +
                "</td>" +
                "</tr>"
        })
        document.getElementById("users-table").innerHTML = tmp
    }
}

roles = getRoles()
users = getUsers()
updateUsersTable( getUsers() )

const buttonDelete = $(".my-delete-btn");
buttonDelete.click(
    function () {
        $.ajax("/api/users/" + $(this).attr("value"), {
            method: "DELETE",
            // data: {id: $(this).attr("value")}, //в rest-контроллер будет передан id=1 (см. value из тэга button выше)
            dataType: "text",
            success: function (msg) {
                $("#users")
                    .find("#" + msg) //ищем div с id=1
                    .remove();

            }
        })
    }
)

