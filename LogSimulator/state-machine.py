from state import BruteForceAttackState, NoAlarmState


class StateMachine:
    def __init__(self, _state_dict):
        self.state_dict = _state_dict

    def run_all(self, _path):
        for _state, _iterations in self.state_dict.items():
            _current_state = _state(_path, _iterations)
            _current_state.run()


if __name__ == '__main__':
    state_dict = {
        BruteForceAttackState: 50,
        NoAlarmState: 8
    }
    state_machine = StateMachine(state_dict)
    state_machine.run_all("file.txt")
