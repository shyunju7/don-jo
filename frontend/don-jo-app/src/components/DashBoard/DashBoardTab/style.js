import styled from "styled-components";

export const TabWrapper = styled.div`
  display: flex;
  width: 100%;
  max-width: 53.75rem;
  justify-content: flex-start;
  align-items: flex-end;
  margin-top: 1.25rem;
  margin-bottom: 2.5rem;
`;

export const Tabs = styled.ul`
  width: 100%;
  list-style: none;
  border-bottom: 0.125rem solid #eaeaea;
`;
export const TabItem = styled.li`
  position: relative;
  display: inline-block;
  padding-bottom: 0.625rem;
  margin: 0 1.25rem;
  cursor: pointer;
  font-family: ${(props) => props.current && "RobotoMedium"};
  color: ${(props) =>
    props.current ? "var(--color-text)" : "var(--color-text-secondary)"};

  &:first-child {
    margin-left: 0rem;
  }
`;

export const Indicator = styled.hr`
  position: absolute;
  bottom: -0.625rem;
  width: 100%;
  border: 0.125rem solid var(--color-text);
  border-radius: 0.25rem;
  opacity: ${(props) => (props.current ? 1 : 0)};
`;
