package br.com.bat.correios.service;

import br.com.bat.correios.CorreiosApplication;
import br.com.bat.correios.exceptions.NoContentException;
import br.com.bat.correios.exceptions.NotReadyException;
import br.com.bat.correios.model.Address;
import br.com.bat.correios.model.AddressStatus;
import br.com.bat.correios.model.Status;
import br.com.bat.correios.repository.AddressRepository;
import br.com.bat.correios.repository.AddressStatusRepository;
import br.com.bat.correios.repository.SetupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CorreiosService {

  private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private AddressStatusRepository addressStatusRepository;

  @Value("${setup.on.startup}")
  private boolean setupOnStartup;

  @Autowired
  private SetupRepository setupRepository;

  public Status getStatus() {
    return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
        .orElse(AddressStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
  }
  public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
    if(!this.getStatus().equals(Status.READY)) {
      throw new NotReadyException();
    }
    return addressRepository.findById(zipcode).orElseThrow(NoContentException::new);
  }

  private void saveStatus(Status status) {
    this.addressStatusRepository.save(AddressStatus.builder()
        .id(AddressStatus.DEFAULT_ID)
        .status(status)
        .build()
    );

  }
  @EventListener(ApplicationStartedEvent.class)
  protected void setupOnStartup() {
    if(!setupOnStartup)
      return;

    try  {
      this.setup();
    } catch (Exception exp) {
      CorreiosApplication.close(999);
      logger.error(".setupOnStartup() - Exception", exp);
    }
  }

  public void setup() throws Exception {
    logger.info(".....");
    logger.info(".....");
    logger.info("..... SETUP RUNNING");
    logger.info(".....");
    logger.info(".....");

    if(this.getStatus().equals(Status.NEED_SETUP)) {
      this.saveStatus(Status.SETUP_RUNNING);
      try {
        this.addressRepository.saveAll(this.setupRepository.getFormOrigin());
      } catch (Exception exp) {
        this.saveStatus(Status.NEED_SETUP);
        throw exp;
      }

      this.saveStatus(Status.READY);
    }


    logger.info(".....");
    logger.info(".....");
    logger.info("..... SERVICE READY");
    logger.info(".....");
    logger.info(".....");
  }
}
