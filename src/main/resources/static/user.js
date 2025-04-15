$(async function() {
    await thisUser()
})
async function thisUser() {
    let responsePromise = fetch("http://localhost:8080/api/show");
    responsePromise
        .then(res => res.json())
        .then(user => {
            $('#headerUsername').append(user.username)
            $('#headerRoles').append(user.roles.map(role => " " + role.name.substring(5)))
            let temp = ''
            console.log(user)
            temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="username${user.username}">${user.username}</td>
                <td id="age${user.age}">${user.age}</td>
                <td id="roles${user.roles}">${user.roles.map(role => role.name.substring(5))}</td>
                </tr>
                `

            document.getElementById('userPanelBody').innerHTML = temp;
        })
}