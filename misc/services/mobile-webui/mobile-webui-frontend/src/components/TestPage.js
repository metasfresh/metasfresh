import React from 'react';
import { Link } from 'react-router-dom';

const TestPage = () => {
  return (
    <div>
      <h1>Test Page !!</h1>
      <Link to="/">Go to Dashboard</Link>
    </div>
  )
}

export default TestPage;
