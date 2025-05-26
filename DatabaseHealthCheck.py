import pg8000

def drop_table(host, port, dbname, user, password, table_name):
    try:
        conn = pg8000.connect(
            host=host,
            port=port,
            database=dbname,
            user=user,
            password=password,
            timeout=5
        )
        cursor = conn.cursor()
        cursor.execute(f'DROP TABLE IF EXISTS "{table_name}";')
        conn.commit()
        cursor.close()
        conn.close()
        print(f"Table '{table_name}' dropped successfully.")
        return True
    except Exception as e:
        print(f"Failed to drop table '{table_name}': {e}")
        return False

if __name__ == "__main__":
    drop_table("localhost", 5433, "mydatabase", "myuser", "mypassword", "users")
