import React, { useState } from "react";
import axios from "axios";

function UserUpdate({ user, onUpdate }) {
  const [updatedUser, setUpdatedUser] = useState({
    username: user.username,
    mail: user.mail,
    age: user.age,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedUser({ ...updatedUser, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(
        `http://localhost:8080/api/${user.id}`,
        updatedUser
      );
      onUpdate(response.data); // Call the onUpdate callback with the updated user data
    } catch (error) {
      console.error("Error updating user:", error);
    }
  };

  return (
    <div>
      <h2>Update User</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={updatedUser.username}
          onChange={handleInputChange}
        />
        <input
          type="text"
          name="mail"
          placeholder="Email"
          value={updatedUser.mail}
          onChange={handleInputChange}
        />
        <input
          type="number"
          name="age"
          placeholder="Age"
          value={updatedUser.age}
          onChange={handleInputChange}
        />
        <button type="submit">Update</button>
      </form>
    </div>
  );
}

export default UserUpdate;
