import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getAuth, GoogleAuthProvider } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyAQSdHLclce_GV7QYZqog7f6okUO_F2fR0",
  authDomain: "coding-collaborator.firebaseapp.com",
  projectId: "coding-collaborator",
  storageBucket: "coding-collaborator.firebasestorage.app",
  messagingSenderId: "809137716222",
  appId: "1:809137716222:web:82c5b90389781eed70deaf",
  measurementId: "G-Z3MQMD2YN6"
};

const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const provider = new GoogleAuthProvider();
const analytics = getAnalytics(app);