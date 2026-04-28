export const deepEqual = (obj1, obj2) => {
  // If both are the same reference or primitive value, return true
  if (obj1 === obj2) return true;

  // If either is null or not an object, return false (one might be primitive, the other isn't)
  if (obj1 === null || obj2 === null || typeof obj1 !== 'object' || typeof obj2 !== 'object') {
    return false;
  }

  // Get keys of both objects
  const keys1 = Object.keys(obj1);
  const keys2 = Object.keys(obj2);

  // If key lengths differ, the objects are not equal
  if (keys1.length !== keys2.length) return false;

  // Check each key's value recursively
  for (let key of keys1) {
    if (!keys2.includes(key) || !deepEqual(obj1[key], obj2[key])) {
      return false;
    }
  }

  return true;
};
