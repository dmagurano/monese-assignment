#!/bin/bash

wait_for_db() {
    echo "Waiting for the database to be ready..."
    export STATUS=0
    i=0
    while [[ $STATUS -eq 0 ]] && [[ $i -lt 30 ]]; do
        sleep 1
        i=$((i+1))
        STATUS=$(grep 'SQL Server is now ready for client connections' /var/opt/mssql/log/errorlog 2>/dev/null | wc -l )
    done

    if [[ $STATUS -eq 0 ]]; then
        echo "Database startup failed in $i seconds"
        exit
    fi

    sleep 3
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    wait_for_db "$@"
fi