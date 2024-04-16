$(async function () {
    await newUser();
});

async function newUser() {

    const form = document.forms["new"];

    form.addEventListener('submit', addNewUser)

    function addNewUser(e) {
        e.preventDefault();

        const selected_options = document.querySelector('#AddNewUserRoles').selectedOptions;

        const rolesNamesArray = new Array(selected_options.length);
        for (let i = 0; i < selected_options.length; i++) {
            rolesNamesArray[i] = selected_options[i].value;
        }

        fetch(`http://localhost:8080/api/admin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                firstName: form.firstName.value,
                lastName: form.lastName.value,
                email: form.email.value,
                roles: rolesNamesArray
            })
        }).then(() => {
            form.reset();
            allUsers();
            $('#usersTableTab').click();

        })
    }
}

