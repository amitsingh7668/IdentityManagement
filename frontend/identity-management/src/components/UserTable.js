import React, { useEffect, useState } from "react";
import axios from "axios";
import { toast } from 'react-toastify';

const roles = ["ADMIN", "VIEW", "EDIT"];

function UserTable() {
  const [users, setUsers] = useState([]);
  const [newUser, setNewUser] = useState({ name: "", email: "", role: "USER" });
  const [error, setError] = useState("");


  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    const res = await axios.get("http://localhost:8080/api/users");
    setUsers(res.data);
  };

  const handleRoleChange = (userId, newRole) => {
    setUsers(prev =>
      prev.map(user =>
        user.id === userId ? { ...user, role: newRole } : user
      )
    );
  };

  const saveUserRole = async (user) => {
    try {
      await axios.put(
        `http://localhost:8080/api/users/roleUpdate/${user.id}`,
        { id: user.id, role: user.role }
      );
      alert("User role updated");
      fetchUsers();
    } catch (error) {
      alert("Error updating role");
    }
  };

  const deleteUser = async (id) => {
    await axios.delete(`http://localhost:8080/api/users/${id}`);
    fetchUsers();
  };

  const addUser = async () => {
    try {
      await axios.post("http://localhost:8080/api/users", newUser);
      setNewUser({ name: "", email: "", role: "USER" });
      document.getElementById("closeModal").click();
      fetchUsers();
    } catch (error) {
      const message =
        error?.response?.data?.message || "An unexpected error occurred.";
      alert(message);
    }
  };
  

  return (
    <div className="d-flex justify-content-center">
      <div style={{ maxWidth: "900px", width: "100%" }}>
        <h2 className="text-center mb-4">User Management</h2>

        <div className="text-end mb-3">
          <button className="btn btn-success" data-bs-toggle="modal" data-bs-target="#addUserModal">
            ➕ Add User
          </button>
        </div>

        <table className="table table-striped table-bordered shadow-sm rounded">
          <thead className="table-dark">
            <tr>
              <th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map(user => (
              <tr key={user.id}>
                <td>{user.id}</td><td>{user.name}</td><td>{user.email}</td>
                <td>
                  <select
                    className="form-select"
                    value={user.role}
                    onChange={(e) => handleRoleChange(user.id, e.target.value)}
                  >
                    {roles.map(r => (
                      <option key={r} value={r}>{r}</option>
                    ))}
                  </select>
                </td>
                <td>
                  <button className="btn btn-primary btn-sm me-2" onClick={() => saveUserRole(user)}>Save</button>
                  <button className="btn btn-danger btn-sm" onClick={() => deleteUser(user.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Add User Modal */}
        <div className="modal fade" id="addUserModal" tabIndex="-1" aria-hidden="true">
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Add New User</h5>
                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div className="modal-body">
                <input
                  type="text"
                  placeholder="Name"
                  className="form-control mb-2"
                  value={newUser.name}
                  onChange={e => setNewUser({ ...newUser, name: e.target.value })}
                />
                <input
                  type="email"
                  placeholder="Email"
                  className="form-control mb-2"
                  value={newUser.email}
                  onChange={e => setNewUser({ ...newUser, email: e.target.value })}
                />
                <select
                  className="form-select"
                  value={newUser.role}
                  onChange={e => setNewUser({ ...newUser, role: e.target.value })}
                >
                  {roles.map(r => <option key={r}>{r}</option>)}
                </select>
              </div>
              <div className="modal-footer">
                <button id="closeModal" type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" className="btn btn-success" onClick={addUser}>Add User</button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default UserTable;
