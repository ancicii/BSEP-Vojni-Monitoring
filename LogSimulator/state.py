import numpy as np
import time
import uuid
from datetime import datetime


log_host = "agent1"


class State:
    def __init__(self, path, iterations):
        self.path = path
        self.iterations = iterations

    def run(self):
        assert 0, "run not implemented"


class BruteForceAttackState(State):
    def run(self):
        os_val = np.random.choice(['Windows7', 'Windows10', 'Ubuntu16', 'MacOS10'])
        addr_val = np.random.choice(['127.0.0.6', '127.0.0.7', '127.0.0.13', '127.0.0.42'])
        for i in range(self.iterations):
            timestamp = datetime.now()
            log_id = uuid.uuid4()
            message = "Unsuccessful login attempt"
            os = os_val
            log_type = "Informational"
            host = log_host
            log_machine = addr_val

            log = str(timestamp)+"|"+str(log_id)+"|"+message+"|"+os+"|"+log_type+"|"+host+"|"+log_machine
            print(log)

            f = open(self.path, "a")
            f.write(log + "\n")
            f.close()
            time.sleep(0.2)


class NoAlarmState(State):
    def run(self):
        for i in range(self.iterations):
            os_val = np.random.choice(['Windows7', 'Windows10', 'Ubuntu16', 'MacOS10'])
            addr_val = np.random.choice(['127.0.0.6', '127.0.0.7', '127.0.0.13', '127.0.0.42'])
            timestamp = datetime.now()
            log_id = uuid.uuid4()
            message = "Unsuccessful login attempt"
            os = os_val
            log_type = "Informational"
            host = log_host
            log_machine = addr_val

            log = str(timestamp)+"|"+str(log_id)+"|"+message+"|"+os+"|"+log_type+"|"+host+"|"+log_machine
            print(log)

            f = open(self.path, "a")
            f.write(log + "\n")
            f.close()
            time.sleep(1.5)
