class Dentist extends User.Medical {
    public Dentist(int id, String userName, int pin) {
        super(id, userName, pin);
        setRole("Dentist");
    }

    @Override
    void viewData() {
        super.viewData();
        if (Administration.currentPatient.getConsults() != null) {
            System.out.format("%-17s \n", "Dentist consult history:");
            for (Consult consult : Administration.currentPatient.getConsults()) {
                if (consult.getUser().getRole().equals("Dentist") ) {
                    System.out.format("> %s\n", consult.getAllDetails());
                }
            }
        }
    }
}