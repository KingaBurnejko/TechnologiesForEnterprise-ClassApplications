package lab.requests.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.html.HtmlDataTable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lab.requests.data.RequestRepository;
import lab.requests.entities.Request;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
@Named
public class RequestsList {
    @Inject
    private RequestRepository requestRepository;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Size(min = 3, max = 60, message = "{request.size}")
    private String newRequest;

    public String getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(String newRequest) {
        this.newRequest = newRequest;
    }

    private HtmlDataTable requestsDataTable;

    public HtmlDataTable getRequestsDataTable() {
        return requestsDataTable;
    }

    public void setRequestsDataTable(HtmlDataTable requestsDataTable) {
        this.requestsDataTable = requestsDataTable;
    }

    @Transactional
    public String addRequest()
    {
        Request request = new Request();

        request.setRequestDate(LocalDate.now());
        request.setRequestText(newRequest);

        requestRepository.create(request);

        setNewRequest("");
        return "requestsList?faces-redirect=true";
    }

    @Transactional
    public String deleteRequest() {
        Request req =
                (Request) getRequestsDataTable().getRowData();
        requestRepository.remove(req);

        return "requestsList?faces-redirect=true";
    }
}
