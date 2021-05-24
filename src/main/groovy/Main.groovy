class Main {

    static void main(String[] args) {

        Calculator calculator = new Calculator()

        assert calculator.calculate("2+2") == 4
        assert calculator.calculate("2+2*2") == 6
        assert calculator.calculate("(2+2)*2") == 8
        assert calculator.calculate("(9*31)-(28-9)") == 260

        print calculator.calculate("2+2")
    }
}
