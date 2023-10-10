import React, { useState } from "react";
import axios from "axios";

function UserForm({ onSave }) {
  const [newUser, setNewUser] = useState({ username: "", mail: "", age: 0 });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewUser({ ...newUser, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/", newUser);
      onSave(response.data);
      setNewUser({ username: "", mail: "", age: 0 });
    } catch (error) {
      console.error("Error creating user:", error);
    }
  };

  return (
    <div>
      <h2>Add User</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={newUser.username}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="mail"
          placeholder="Email"
          value={newUser.mail}
          onChange={handleInputChange}
        />
        <input
          type="number"
          name="age"
          placeholder="Age"
          value={newUser.age}
          onChange={handleInputChange}
        />
        <button type="submit">Add</button>
      </form>
    </div>
  );
}

export default UserForm;
