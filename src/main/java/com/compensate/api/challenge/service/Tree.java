package com.compensate.api.challenge.service;

import com.compensate.api.challenge.model.Node;
import java.io.Serializable;
import java.util.List;

public interface Tree<T extends Node> extends Serializable {

  T getRoot(T node);

  List<T> getChildren(T node);
}
