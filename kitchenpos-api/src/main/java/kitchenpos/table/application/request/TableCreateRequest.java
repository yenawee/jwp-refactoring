package kitchenpos.table.application.request;

public class TableCreateRequest {

    private final Integer numberOfGuests;
    private final Boolean empty;

    public TableCreateRequest(Integer numberOfGuests, Boolean empty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public Boolean getEmpty() {
        return empty;
    }
}
