import React, { useEffect, useState } from 'react';
import "../../CSS/Log.css";

function Log() {
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8081/admin/logs')
            .then((response) => response.json())
            .then((data) => {
                const sortedLogs = data.sort((a, b) => new Date(b.logDate) - new Date(a.logDate));
                setLogs(sortedLogs);
            })
            .catch((error) => console.error('Hata:', error));
    }, []);

    return (
        <div className="log-page">
            <h1 className="log-title">Logs</h1>
            {logs.length > 0 ? (
                logs
                    .filter((log) => log !== null || log.user !== null || log.order !== null)
                    .map((log, index) => (
                        <div key={index} className="log-entry">
                            <p className="log-date"><strong>Date:</strong> {log.logDate}</p>
                            <p className="log-type"><strong>Log Type:</strong> {log.logType}</p>
                            <p className="log-result"><strong>Result:</strong> {log.transactionResult}</p>
                            <hr className="log-divider" />
                        </div>
                    ))
            ) : (
                <p className="log-loading">Loading or no data found.</p>
            )}
        </div>
    );
}

export default Log;
