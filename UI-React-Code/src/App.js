import React, { useEffect, useState } from 'react';
import './App.css';
import { fetchDataFromApi, fetchDataFromApi_2, fetchDataFromApi_3 } from './Api'; // Import all API functions
import natwestIcon from './3-NatWest-New-Logo.jpeg'; // Import the SVG file

function App() {
  const [data, setData] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [searchField, setSearchField] = useState('LU Name'); // State to store selected search field
  const [combinedResult, setCombinedResult] = useState(null); // State to store combined JSON

  const fetchApiData = async () => {
    try {
      const api1Data = await fetchDataFromApi();
      const api2Data = await fetchDataFromApi_2();
      const api3Data = await fetchDataFromApi_3(); // Fetch data from the third API

      // Convert Sort Code to consistent format for API 1 and API 2
      const updatedApi1Data = api1Data.map(item => ({
        ...item,
        'Sort Code or Store ID': item['Sort Code'] ? item['Sort Code'].toString() : item['Sort Code']
      }));
      const updatedApi2Data = api2Data.map(item => ({
        ...item,
        'Sort Code or Store ID': item['Sort Code'] ? item['Sort Code'].toString() : item['Sort Code']
      }));

      // Add Last transaction and Current condition from API 3
      const updatedApi3Data = api3Data.map(item => ({
        ...item,
        'Last transaction': item['Last transaction'] || '', // Ensure field exists
        'Current condition': item['Current condition'] || '' // Ensure field exists
      }));

      // Combine all API data
      const allData = [...updatedApi1Data, ...updatedApi2Data];

      // Merge data from API 3 into the combined data
      const combinedData = allData.map(item => {
        const api3Item = updatedApi3Data.find(api3Item => 
          api3Item['Sort Code'] === item['Sort Code or Store ID'] || 
          api3Item['Post Code'] === item['Post Code']
        ) || {};
        
        return {
          ...item,
          'Last transaction': api3Item['Last transaction'] || '',
          'Current condition': api3Item['Current condition'] || ''
        };
      });

      setData(combinedData);
    } catch (error) {
      console.error('Error fetching API data:', error);
    }
  };

  useEffect(() => {
    fetchApiData();
  }, []);

  useEffect(() => {
    const performSearch = () => {
      let filteredData = [];

      if (searchField === 'Post Code' && searchTerm) {
        filteredData = data.filter(item => 
          item['Post Code'] && item['Post Code'].toLowerCase().includes(searchTerm.toLowerCase())
        );
      } else if (searchField === 'LU Name' && searchTerm) {
        filteredData = data.filter(item => 
          item['LU Name'] && item['LU Name'].toLowerCase().includes(searchTerm.toLowerCase())
        );
      } else if (searchField === 'Sort Code or Store ID' && searchTerm) {
        filteredData = data.filter(item => 
          item['Sort Code or Store ID'] && item['Sort Code or Store ID'].toString().includes(searchTerm)
        );
      }

      if (filteredData.length > 0) {
        const combinedResults = filteredData.reduce((acc, curr) => {
          const existing = acc.find(item => 
            (searchField === 'Post Code' && item['Post Code'] === curr['Post Code']) ||
            (searchField !== 'Post Code' && item[searchField] === curr[searchField])
          );

          if (existing) {
            // Concatenate specific fields if they exist and differ
            existing['Last transaction'] = existing['Last transaction'] && curr['Last transaction']
              ? `${existing['Last transaction']} - ${curr['Last transaction']}`
              : existing['Last transaction'] || curr['Last transaction'];
            
            existing['Current condition'] = existing['Current condition'] && curr['Current condition']
              ? `${existing['Current condition']} - ${curr['Current condition']}`
              : existing['Current condition'] || curr['Current condition'];

            Object.keys(curr).forEach(key => {
              if (key !== 'Last transaction' && key !== 'Current condition') {
                if (curr[key] && existing[key] && existing[key] !== curr[key]) {
                  existing[key] = `${existing[key]} - ${curr[key]}`;
                } else {
                  existing[key] = curr[key];
                }
              }
            });
          } else {
            acc.push({ ...curr });
          }
          return acc;
        }, []);

        console.log('Combined JSON:', combinedResults); // Print to console
        setCombinedResult(combinedResults);
      } else {
        setCombinedResult(null);
      }
    };

    performSearch();
  }, [searchTerm, searchField, data]);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleFieldChange = (event) => {
    setSearchField(event.target.value);
  };

  return (
    <div className="App">
      <header className="App-header">
      <img src={natwestIcon} alt="NatWest Icon" className="App-icon" /> {/* Add SVG icon */}
        <h3>Live ATM Intel</h3>
        <div className="search-container">
          <label htmlFor="searchField">Search Field</label>
          <select id="searchField" onChange={handleFieldChange} value={searchField}>
            <option value="LU Name">LU Name</option>
            <option value="Sort Code or Store ID">Sort Code or Store ID</option>
            <option value="Post Code">Post Code</option>
          </select>
          <label htmlFor="searchInput">Search Term</label>
          <input
            id="searchInput"
            type="text"
            placeholder={`Search by ${searchField}`}
            value={searchTerm}
            onChange={handleSearchChange}
          />
        </div>
        {combinedResult ? (
          <div className="card-container">
            {combinedResult.map((item, index) => (
              <div key={index} className="card">
                {Object.entries(item).map(([key, value], idx) => (
                  <div key={idx} className="card-row">
                    <strong className="card-key">{key}:</strong> 
                    <span className="card-value">{value}</span>
                  </div>
                ))}
              </div>
            ))}
          </div>
        ) : (
          <p>No results found</p>
        )}
      </header>
    </div>
  );
}

export default App;
