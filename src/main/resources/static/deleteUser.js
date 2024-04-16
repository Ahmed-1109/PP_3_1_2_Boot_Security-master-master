$(async function() {
    deleteUser();
});
function deleteUser() {
    const deleteForm = document.forms["formDeleteUser"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch(`http://localhost:8080/api/admin/` + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                allUsers();
            })
    })
}

$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    deletUser2(id);
})

async function deletUser2(id) {
    const user = await getUser(id);
    const form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.firstName.value = user.firstName;
    form.lastName.value = user.lastName;
    form.email.value = user.email;
    form.password.value = user.password;
}

async function getUser(id) {
    const url = `http://localhost:8080/api/admin/` + id;
    const response = await fetch(url);
    return await response.json();
}