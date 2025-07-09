
// import React from "react";

// function Client({ username, photoURL, email }) {
//   return (
//     <div className="d-flex align-items-center mb-3">
//       <img
//         src={photoURL || "/images/default-avatar.png"} 
//         alt={username}
//         style={{ width: 40, height: 40, borderRadius: "14px", marginRight: 10 }}
//       />
//       <div>
//         <span className="mx-2">{username.toString()}</span>
//         <br />
//         <small style={{ color: "#ffffff" }}>{email}</small> {/* Use white text */}
//       </div>
//     </div>
//   );
// }

// export default Client;
import React from "react";
import "./Client.css";

function Client({ username, photoURL, email }) {
  return (
    <div className="client">
      <div className="client-photo">
        {photoURL ? (
          <img
            src={photoURL}
            alt={`${username}'s profile`}
            className="client-img"
            onError={(e) => {
              e.target.src = "https://via.placeholder.com/40"; // Fallback image
            }}
          />
        ) : (
          <div className="client-img-placeholder">{username.charAt(0).toUpperCase()}</div>
        )}
      </div>
      <div className="client-info">
        <span className="client-username">{username || "Anonymous"}</span>
        <span className="client-email">{email || "No email provided"}</span>
      </div>
    </div>
  );
}

export default Client;