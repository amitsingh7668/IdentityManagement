import React from "react";
import UserTable from "./components/UserTable";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // includes Popper


function App() {
  return (
    <div className="container">
      <UserTable />
    </div>
  );
}

export default App;
