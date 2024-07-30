// Api.js
export const fetchDataFromApi = async () => {
  try {
    const response = await fetch('http://localhost:8003/atms/load1'); // Replace with your API endpoint
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    console.log('API 1 Response:', data); // Log the API response
    return data; // Assuming data is an array of objects
  } catch (error) {
    console.error('Error fetching data from api 1:', error);
    return [];
  }
};

export const fetchDataFromApi_2 = async () => {
  try {
    const response = await fetch('http://localhost:8003/atms/load2'); // Replace with your API endpoint
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    console.log('API 2 Response:', data); // Log the API response
    return data; // Assuming data is an array of objects
  } catch (error) {
    console.error('Error fetching data from api 2:', error);
    return [];
  }
};

// New function to fetch data from the third API
export const fetchDataFromApi_3 = async () => {
  try {
    const response = await fetch('http://localhost:8003/atms/load3'); // Replace with your API endpoint
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    console.log('API 3 Response:', data); // Log the API response
    return data; // Assuming data is an array of objects
  } catch (error) {
    console.error('Error fetching data from api 3:', error);
    return [];
  }
};
