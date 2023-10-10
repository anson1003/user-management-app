import React, { useState, useEffect } from "react";
import axios from "axios";
import UserForm from "./UserForm";
import UserUpdate from "./UserUpdate";

function App() {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/");
      setUsers(response.data);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const createUser = async (newUser) => {
    try {
      setUsers([...users, newUser]);
    } catch (error) {
      console.error("Error creating user:", error);
    }
  };

  const updateUser = async (updatedUser) => {
    try {
      const response = await axios.get("http://localhost:8080/api/");
      setUsers(response.data); // Refresh the user list
      setSelectedUser(null);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const deleteUser = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/${id}`);
      const updatedUsers = users.filter((user) => user.id !== id);
      setUsers(updatedUsers);
    } catch (error) {
      console.error("Error deleting user:", error);
    }
  };

  return (
    <div className="App">
      <h1>User Management App</h1>
      <UserForm onSave={createUser} />
      <div>
        <h2>User List</h2>
        <table>
          <thead>
            <tr>
              <th>Username</th>
              <th>Email</th>
              <th>Age</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.username}</td>
                <td>{user.mail}</td>
                <td>{user.age}</td>
                <td>
                  <button onClick={() => deleteUser(user.id)}>Delete</button>
                  <button onClick={() => setSelectedUser(user)}>Update</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {selectedUser && (
        <UserUpdate user={selectedUser} onUpdate={updateUser} /> // Pass the onUpdate callback
      )}
    </div>
  );
}

export default App;
